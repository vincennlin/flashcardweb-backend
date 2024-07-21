package com.vincennlin.flashcardservice.service.impl;

import com.vincennlin.flashcardservice.client.NoteServiceClient;
import com.vincennlin.flashcardservice.payload.flashcard.dto.impl.*;
import com.vincennlin.flashcardservice.payload.flashcard.type.FlashcardType;
import com.vincennlin.flashcardservice.entity.AbstractFlashcard;
import com.vincennlin.flashcardservice.entity.impl.*;
import com.vincennlin.flashcardservice.exception.FlashcardTypeException;
import com.vincennlin.flashcardservice.exception.ResourceNotFoundException;
import com.vincennlin.flashcardservice.exception.ResourceOwnershipException;
import com.vincennlin.flashcardservice.exception.WebAPIException;
import com.vincennlin.flashcardservice.payload.flashcard.dto.AbstractFlashcardDto;
import com.vincennlin.flashcardservice.repository.FlashcardRepository;
import com.vincennlin.flashcardservice.repository.OptionRepository;
import com.vincennlin.flashcardservice.service.FlashcardService;
import feign.FeignException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class FlashcardServiceImpl implements FlashcardService {

    private static final Logger logger = LoggerFactory.getLogger(FlashcardServiceImpl.class);

    private FlashcardRepository flashcardRepository;

    private ModelMapper modelMapper;

    private OptionRepository optionRepository;

    private NoteServiceClient noteServiceClient;

    @Override
    public List<AbstractFlashcardDto> getFlashcardsByNoteId(Long noteId) {

        authorizeOwnershipByNoteId(noteId);

        List<AbstractFlashcard> flashcards = flashcardRepository.findByNoteId(noteId);

        return flashcards.stream().map(flashcard -> {
            authorizeOwnershipByFlashcardOwnerId(flashcard.getUserId());
            return mapToDto(flashcard);
        }).toList();
    }

    @Override
    public AbstractFlashcardDto getFlashcardById(Long flashcardId) {

        AbstractFlashcard flashcard = flashcardRepository.findById(flashcardId).orElseThrow(() ->
                new ResourceNotFoundException("Flashcard", "id", flashcardId));

        authorizeOwnershipByFlashcardOwnerId(flashcard.getUserId());

        return mapToDto(flashcard);
    }

    @Transactional
    @Override
    public AbstractFlashcardDto createFlashcard(Long noteId, AbstractFlashcardDto flashcardDto) {

        authorizeOwnershipByNoteId(noteId);

        flashcardDto.setUserId(getCurrentUserId());
        flashcardDto.setNoteId(noteId);

        if (flashcardDto.getType() == FlashcardType.FILL_IN_THE_BLANK) {
            return createFillInTheBlankFlashcard((FillInTheBlankFlashcardDto) flashcardDto);
        } else if (flashcardDto.getType() == FlashcardType.MULTIPLE_CHOICE) {
            return createMultipleChoiceFlashcard((MultipleChoiceFlashcardDto) flashcardDto);
        } else {

            AbstractFlashcard flashcard = flashcardDto.mapToEntity();

            AbstractFlashcard newFlashcard = flashcardRepository.save(flashcard);

            return mapToDto(newFlashcard);
        }
    }

    public AbstractFlashcardDto createFillInTheBlankFlashcard(FillInTheBlankFlashcardDto fillInTheBlankFlashcardDto) {

        FillInTheBlankFlashcard fillInTheBlankFlashcard = modelMapper.map(fillInTheBlankFlashcardDto, FillInTheBlankFlashcard.class);

        fillInTheBlankFlashcard.getInBlankAnswers().forEach(
                inBlankAnswers -> inBlankAnswers.setFlashcard(fillInTheBlankFlashcard));

        AbstractFlashcard newFlashcard = flashcardRepository.save(fillInTheBlankFlashcard);

        return mapToDto(newFlashcard);
    }

    @Transactional
    public AbstractFlashcardDto createMultipleChoiceFlashcard(MultipleChoiceFlashcardDto multipleChoiceFlashcardDto) {

        if (multipleChoiceFlashcardDto.getAnswerIndex() > multipleChoiceFlashcardDto.getOptions().size()) {
            throw new IllegalArgumentException("Answer index is out of range of options");
        }

        MultipleChoiceFlashcard multipleChoiceFlashcard = (MultipleChoiceFlashcard) multipleChoiceFlashcardDto.mapToEntity();

        List<Option> options = multipleChoiceFlashcard.getOptions();
        options.forEach(option -> option.setFlashcard(multipleChoiceFlashcard));
        List<Option> savedOptions = optionRepository.saveAll(options);

        multipleChoiceFlashcard.setAnswerOption(savedOptions.get(multipleChoiceFlashcardDto.getAnswerIndex() - 1));

        AbstractFlashcard newFlashcard = flashcardRepository.save(multipleChoiceFlashcard);

        return mapToDto(newFlashcard);
    }

    @Override
    public AbstractFlashcardDto updateFlashcard(Long flashcardId, AbstractFlashcardDto flashcardDto) {

        AbstractFlashcard flashcard = flashcardRepository.findById(flashcardId).orElseThrow(() ->
                new ResourceNotFoundException("Flashcard", "id", flashcardId));

        authorizeOwnershipByFlashcardOwnerId(flashcard.getUserId());

        if (flashcardDto.getType() != flashcard.getType()) {
            throw new FlashcardTypeException(flashcardId, flashcard.getType(), flashcardDto.getType());
        }

        flashcardDto.setId(flashcard.getId());
        flashcardDto.setUserId(flashcard.getUserId());
        flashcardDto.setNoteId(flashcard.getNoteId());

        AbstractFlashcard newFlashcard = null;

        if (flashcardDto.getType() == FlashcardType.FILL_IN_THE_BLANK) {
            newFlashcard = updateFillInTheBlankFlashcard((FillInTheBlankFlashcard) flashcard,
                                                            (FillInTheBlankFlashcard) flashcardDto.mapToEntity());
        } else if (flashcardDto.getType() == FlashcardType.MULTIPLE_CHOICE) {
            newFlashcard = updateMultipleChoiceFlashcard((MultipleChoiceFlashcard) flashcard,
                                                            (MultipleChoiceFlashcardDto) flashcardDto);
        } else {
            newFlashcard = flashcardDto.mapToEntity();
        }

        AbstractFlashcard updatedFlashcard = flashcardRepository.save(newFlashcard);

        return mapToDto(updatedFlashcard);
    }

    public AbstractFlashcard updateFillInTheBlankFlashcard(FillInTheBlankFlashcard flashcard,
                                                           FillInTheBlankFlashcard newFlashcard) {

        List<InBlankAnswer> inBlankAnswers = flashcard.getInBlankAnswers();
        int blankAnswerSize = inBlankAnswers.size();

        if (newFlashcard.getInBlankAnswers().size() != blankAnswerSize) {
            throw new IllegalArgumentException("Number of blank answers cannot be changed");
        }

        for (int i = 0; i < blankAnswerSize; i++) {
            inBlankAnswers.get(i).setText(newFlashcard.getInBlankAnswers().get(i).getText());
            inBlankAnswers.get(i).setFlashcard(flashcard);
        }

        newFlashcard.setInBlankAnswers(inBlankAnswers);

        return newFlashcard;
    }

    public AbstractFlashcard updateMultipleChoiceFlashcard(MultipleChoiceFlashcard flashcard,
                                                              MultipleChoiceFlashcardDto flashcardDto) {

        List<Option> options = flashcard.getOptions();
        int optionSize = options.size();

        if (flashcardDto.getOptions().size() != optionSize) {
            throw new IllegalArgumentException("Number of options cannot be changed");
        } else if (flashcardDto.getAnswerIndex() > optionSize) {
            throw new IllegalArgumentException("Answer index is out of range of options");
        }

        MultipleChoiceFlashcard newFlashcard = (MultipleChoiceFlashcard) flashcardDto.mapToEntity();

        for (int i = 0; i < optionSize; i++) {
            options.get(i).setText(flashcardDto.getOptions().get(i).getText());
            options.get(i).setFlashcard(newFlashcard);
        }

        List<Option> savedOptions = optionRepository.saveAll(options);
        newFlashcard.setOptions(savedOptions);
        newFlashcard.setAnswerOption(savedOptions.get(flashcardDto.getAnswerIndex() - 1));

        return newFlashcard;
    }

    @Transactional
    @Override
    public void deleteFlashcardById(Long flashcardId) {

        AbstractFlashcard flashcard = flashcardRepository.findById(flashcardId).orElseThrow(() ->
                new ResourceNotFoundException("Flashcard", "id", flashcardId));

        authorizeOwnershipByFlashcardOwnerId(flashcard.getUserId());

        flashcardRepository.delete(flashcard);
    }

    @Transactional
    @Override
    public void deleteFlashcardsByNoteId(Long noteId) {

        authorizeOwnershipByNoteId(noteId);

        flashcardRepository.deleteByNoteId(noteId);
    }

    private Long getCurrentUserId() {
        return Long.parseLong(getAuthentication().getPrincipal().toString());
    }

    private String getAuthorization() {
        return getAuthentication().getCredentials().toString();
    }

    private void authorizeOwnershipByNoteId(Long noteId) {
        if (!isNoteOwner(noteId) && !containsAuthority("ADVANCED")) {
            throw new ResourceOwnershipException(getCurrentUserId(), "Note");
        }
    }

    private void authorizeOwnershipByFlashcardOwnerId(Long flashcardOwnerId) {
        if (flashcardOwnerId != getCurrentUserId() && !containsAuthority("ADVANCED")) {
            throw new ResourceOwnershipException(getCurrentUserId(), "Flashcard");
        }
    }

    private Boolean containsAuthority(String authorityName) {
        return getAuthentication().getAuthorities().stream().anyMatch(
                authority -> authority.getAuthority().equals(authorityName));
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private Boolean isNoteOwner(Long noteId) {
        ResponseEntity<Boolean> response = null;
        try{
            response = noteServiceClient.isNoteOwner(noteId, getAuthorization());
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage());
            if (e instanceof FeignException && ((FeignException)e).status() == HttpStatus.NOT_FOUND.value())
                throw new ResourceNotFoundException("Note", "id", noteId);
            else if (!(e instanceof ResourceNotFoundException))
                throw new WebAPIException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
            throw e;
        }
        return response.getBody();
    }

    private AbstractFlashcardDto mapToDto(AbstractFlashcard flashcard) {
        return modelMapper.map(flashcard, flashcard.getType().getFlashcardDtoClass());
    }
}

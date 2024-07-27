package com.vincennlin.flashcardservice.service.impl;

import com.vincennlin.flashcardservice.client.NoteServiceClient;
import com.vincennlin.flashcardservice.entity.tag.Tag;
import com.vincennlin.flashcardservice.mapper.FlashcardMapper;
import com.vincennlin.flashcardservice.payload.flashcard.dto.impl.*;
import com.vincennlin.flashcardservice.payload.flashcard.type.FlashcardType;
import com.vincennlin.flashcardservice.entity.flashcard.Flashcard;
import com.vincennlin.flashcardservice.entity.flashcard.impl.*;
import com.vincennlin.flashcardservice.exception.FlashcardTypeException;
import com.vincennlin.flashcardservice.exception.ResourceNotFoundException;
import com.vincennlin.flashcardservice.exception.ResourceOwnershipException;
import com.vincennlin.flashcardservice.exception.WebAPIException;
import com.vincennlin.flashcardservice.payload.flashcard.dto.FlashcardDto;
import com.vincennlin.flashcardservice.repository.FlashcardRepository;
import com.vincennlin.flashcardservice.repository.OptionRepository;
import com.vincennlin.flashcardservice.repository.TagRepository;
import com.vincennlin.flashcardservice.service.FlashcardService;
import feign.FeignException;
import lombok.AllArgsConstructor;
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

    private final FlashcardMapper flashcardMapper;

    private final FlashcardRepository flashcardRepository;
    private final OptionRepository optionRepository;
    private final TagRepository tagRepository;

    private final NoteServiceClient noteServiceClient;

    @Override
    public List<FlashcardDto> getFlashcardsByNoteId(Long noteId) {

        authorizeOwnershipByNoteId(noteId);

        List<Flashcard> flashcards = flashcardRepository.findByNoteId(noteId);

        return flashcards.stream().map(flashcard -> {
            authorizeOwnershipByFlashcardOwnerId(flashcard.getUserId());
            return flashcardMapper.mapToDto(flashcard);
        }).toList();
    }

    @Override
    public FlashcardDto getFlashcardById(Long flashcardId) {

        return flashcardMapper.mapToDto(getFlashcardEntityById(flashcardId));
    }

    @Override
    public List<FlashcardDto> getFlashcardsByTagNames(List<String> tagNames) {

        List<Tag> tagEntities = tagNames.stream().map(tagName ->
                tagRepository.findByTagNameAndUserId(tagName, getCurrentUserId()).orElseThrow(() ->
                        new ResourceNotFoundException("Tag", "tagName", tagName))).toList();

        return flashcardRepository.findByTags(tagEntities).stream().map(flashcardMapper::mapToDto).toList();
    }

    @Override
    public Flashcard getFlashcardEntityById(Long flashcardId) {

        Flashcard flashcard = flashcardRepository.findById(flashcardId).orElseThrow(() ->
                new ResourceNotFoundException("Flashcard", "id", flashcardId.toString()));

        authorizeOwnershipByFlashcardOwnerId(flashcard.getUserId());

        return flashcard;
    }

    @Transactional
    @Override
    public FlashcardDto createFlashcard(Long noteId, FlashcardDto flashcardDto) {

        authorizeOwnershipByNoteId(noteId);

        flashcardDto.setUserId(getCurrentUserId());
        flashcardDto.setNoteId(noteId);

        if (flashcardDto.getType() == FlashcardType.FILL_IN_THE_BLANK) {
            return createFillInTheBlankFlashcard((FillInTheBlankFlashcardDto) flashcardDto);
        } else if (flashcardDto.getType() == FlashcardType.MULTIPLE_CHOICE) {
            return createMultipleChoiceFlashcard((MultipleChoiceFlashcardDto) flashcardDto);
        } else {

            Flashcard flashcard = flashcardMapper.mapToEntity(flashcardDto);

            flashcard.getReviewInfo().setFlashcard(flashcard);

            Flashcard newFlashcard = flashcardRepository.save(flashcard);

            return flashcardMapper.mapToDto(newFlashcard);
        }
    }

    @Transactional
    public FlashcardDto createFillInTheBlankFlashcard(FillInTheBlankFlashcardDto fillInTheBlankFlashcardDto) {

        FillInTheBlankFlashcard fillInTheBlankFlashcard = (FillInTheBlankFlashcard) flashcardMapper.mapToEntity(fillInTheBlankFlashcardDto);

        fillInTheBlankFlashcard.getInBlankAnswers().forEach(
                inBlankAnswers -> inBlankAnswers.setFlashcard(fillInTheBlankFlashcard));

        Flashcard newFlashcard = flashcardRepository.save(fillInTheBlankFlashcard);

        return flashcardMapper.mapToDto(newFlashcard);
    }

    @Transactional
    public FlashcardDto createMultipleChoiceFlashcard(MultipleChoiceFlashcardDto multipleChoiceFlashcardDto) {

        if (multipleChoiceFlashcardDto.getAnswerIndex() > multipleChoiceFlashcardDto.getOptions().size()) {
            throw new IllegalArgumentException("Answer index is out of range of options");
        }

        MultipleChoiceFlashcard multipleChoiceFlashcard = (MultipleChoiceFlashcard) multipleChoiceFlashcardDto.mapToEntity();

        List<Option> options = multipleChoiceFlashcard.getOptions();
        List<Option> savedOptions = optionRepository.saveAll(options);

        multipleChoiceFlashcard.setAnswerOption(savedOptions.get(multipleChoiceFlashcardDto.getAnswerIndex() - 1));

        MultipleChoiceFlashcard newFlashcard = flashcardRepository.save(multipleChoiceFlashcard);
        options.forEach(option -> option.setFlashcard(newFlashcard));

        optionRepository.saveAll(options);

        return flashcardMapper.mapToDto(newFlashcard);
    }

    @Transactional
    @Override
    public List<FlashcardDto> createFlashcards(Long noteId, List<FlashcardDto> flashcardDtoList) {

        return flashcardDtoList.stream().map(flashcardDto ->
                createFlashcard(noteId, flashcardDto)).toList();
    }

    @Override
    public FlashcardDto updateFlashcard(Long flashcardId, FlashcardDto flashcardDto) {

        Flashcard flashcard = flashcardRepository.findById(flashcardId).orElseThrow(() ->
                new ResourceNotFoundException("Flashcard", "id", flashcardId.toString()));

        authorizeOwnershipByFlashcardOwnerId(flashcard.getUserId());

        if (flashcardDto.getType() != flashcard.getType()) {
            throw new FlashcardTypeException(flashcardId, flashcard.getType(), flashcardDto.getType());
        }

        flashcardDto.setId(flashcard.getId());
        flashcardDto.setUserId(flashcard.getUserId());
        flashcardDto.setNoteId(flashcard.getNoteId());

        Flashcard newFlashcard = null;

        if (flashcardDto.getType() == FlashcardType.FILL_IN_THE_BLANK) {
            newFlashcard = updateFillInTheBlankFlashcard((FillInTheBlankFlashcard) flashcard,
                                                            (FillInTheBlankFlashcard) flashcardDto.mapToEntity());
        } else if (flashcardDto.getType() == FlashcardType.MULTIPLE_CHOICE) {
            newFlashcard = updateMultipleChoiceFlashcard((MultipleChoiceFlashcard) flashcard,
                                                            (MultipleChoiceFlashcardDto) flashcardDto);
        } else {
            newFlashcard = flashcardDto.mapToEntity();
        }

        Flashcard updatedFlashcard = flashcardRepository.save(newFlashcard);

        return flashcardMapper.mapToDto(updatedFlashcard);
    }

    public Flashcard updateFillInTheBlankFlashcard(FillInTheBlankFlashcard flashcard,
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

    public Flashcard updateMultipleChoiceFlashcard(MultipleChoiceFlashcard flashcard,
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

        Flashcard flashcard = flashcardRepository.findById(flashcardId).orElseThrow(() ->
                new ResourceNotFoundException("Flashcard", "id", flashcardId.toString()));

        authorizeOwnershipByFlashcardOwnerId(flashcard.getUserId());

        flashcardRepository.delete(flashcard);
    }

    @Transactional
    @Override
    public void deleteFlashcardsByNoteId(Long noteId) {

        authorizeOwnershipByNoteId(noteId);

        flashcardRepository.deleteByNoteId(noteId);
    }

    @Override
    public Long getCurrentUserId() {
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
                throw new ResourceNotFoundException("Note", "id", noteId.toString());
            else if (!(e instanceof ResourceNotFoundException))
                throw new WebAPIException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
            throw e;
        }
        return response.getBody();
    }
}

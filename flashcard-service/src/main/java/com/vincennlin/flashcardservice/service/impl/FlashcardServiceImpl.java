package com.vincennlin.flashcardservice.service.impl;

import com.vincennlin.flashcardservice.client.NoteServiceClient;
import com.vincennlin.flashcardservice.constant.FlashcardType;
import com.vincennlin.flashcardservice.entity.AbstractFlashcard;
import com.vincennlin.flashcardservice.entity.concrete.*;
import com.vincennlin.flashcardservice.exception.FlashcardTypeException;
import com.vincennlin.flashcardservice.exception.ResourceNotFoundException;
import com.vincennlin.flashcardservice.exception.WebAPIException;
import com.vincennlin.flashcardservice.payload.AbstractFlashcardDto;
import com.vincennlin.flashcardservice.payload.NoteClientResponse;
import com.vincennlin.flashcardservice.payload.concrete.FillInTheBlankFlashcardDto;
import com.vincennlin.flashcardservice.payload.concrete.MultipleChoiceFlashcardDto;
import com.vincennlin.flashcardservice.payload.concrete.ShortAnswerFlashcardDto;
import com.vincennlin.flashcardservice.payload.concrete.TrueFalseFlashcardDto;
import com.vincennlin.flashcardservice.repository.FlashcardRepository;
import com.vincennlin.flashcardservice.repository.OptionRepository;
import com.vincennlin.flashcardservice.service.FlashcardService;
import feign.FeignException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class FlashcardServiceImpl implements FlashcardService {

    private FlashcardRepository flashcardRepository;

    private ModelMapper modelMapper;

    private OptionRepository optionRepository;

    private NoteServiceClient noteServiceClient;

    @Override
    public List<AbstractFlashcardDto> getFlashcardsByNoteId(Long noteId) {

        List<AbstractFlashcard> flashcards = flashcardRepository.findByNoteId(noteId).orElseThrow(() ->
                new ResourceNotFoundException("Flashcards", "noteId", noteId));

        return flashcards.stream().map(this::mapToDto).toList();
    }

    @Override
    public AbstractFlashcardDto getFlashcardById(Long flashcardId) {

        AbstractFlashcard flashcard = flashcardRepository.findById(flashcardId).orElseThrow(() ->
                new ResourceNotFoundException("Flashcard", "id", flashcardId));

        return mapToDto(flashcard);
    }

    @Override
    public AbstractFlashcardDto createFlashcard(Long noteId, ShortAnswerFlashcardDto shortAnswerFlashcardDto) {

        NoteClientResponse noteClientResponse = getNoteClientResponseByNoteId(noteId);

        shortAnswerFlashcardDto.setUserId(getCurrentUserId());

        ShortAnswerFlashcard shortAnswerFlashcard = modelMapper.map(shortAnswerFlashcardDto, ShortAnswerFlashcard.class);
        shortAnswerFlashcard.setType(FlashcardType.SHORT_ANSWER);

        shortAnswerFlashcard.setNoteId(noteClientResponse.getId());

        AbstractFlashcard newFlashcard = flashcardRepository.save(shortAnswerFlashcard);

        return modelMapper.map(newFlashcard, ShortAnswerFlashcardDto.class);
    }

    @Override
    public AbstractFlashcardDto createFlashcard(Long noteId, FillInTheBlankFlashcardDto fillInTheBlankFlashcardDto) {

        NoteClientResponse noteClientResponse = getNoteClientResponseByNoteId(noteId);

        fillInTheBlankFlashcardDto.setUserId(getCurrentUserId());

        FillInTheBlankFlashcard fillInTheBlankFlashcard = modelMapper.map(fillInTheBlankFlashcardDto, FillInTheBlankFlashcard.class);
        fillInTheBlankFlashcard.setType(FlashcardType.FILL_IN_THE_BLANK);

        fillInTheBlankFlashcard.setNoteId(noteClientResponse.getId());

        fillInTheBlankFlashcard.getInBlankAnswers().forEach(
                inBlankAnswers -> inBlankAnswers.setFlashcard(fillInTheBlankFlashcard));

        AbstractFlashcard newFlashcard = flashcardRepository.save(fillInTheBlankFlashcard);

        return modelMapper.map(newFlashcard, FillInTheBlankFlashcardDto.class);
    }

    @Transactional
    @Override
    public AbstractFlashcardDto createFlashcard(Long noteId, MultipleChoiceFlashcardDto multipleChoiceFlashcardDto) {

        NoteClientResponse noteClientResponse = getNoteClientResponseByNoteId(noteId);

        multipleChoiceFlashcardDto.setUserId(getCurrentUserId());

        MultipleChoiceFlashcard multipleChoiceFlashcard = modelMapper.map(multipleChoiceFlashcardDto, MultipleChoiceFlashcard.class);
        multipleChoiceFlashcard.setType(FlashcardType.MULTIPLE_CHOICE);
        multipleChoiceFlashcard.setNoteId(noteClientResponse.getId());

        List<Option> options = multipleChoiceFlashcard.getOptions();
        options.forEach(option -> option.setFlashcard(multipleChoiceFlashcard));
        List<Option> savedOptions = optionRepository.saveAll(options);

        if (multipleChoiceFlashcardDto.getAnswerIndex() > savedOptions.size()) {
            throw new IllegalArgumentException("Answer index is out of range of options");
        }
        multipleChoiceFlashcard.setAnswerOption(savedOptions.get(multipleChoiceFlashcardDto.getAnswerIndex() - 1));

        AbstractFlashcard newFlashcard = flashcardRepository.save(multipleChoiceFlashcard);

        return modelMapper.map(newFlashcard, MultipleChoiceFlashcardDto.class);
    }

    @Override
    public AbstractFlashcardDto createFlashcard(Long noteId, TrueFalseFlashcardDto trueFalseFlashcardDto) {

        NoteClientResponse noteClientResponse = getNoteClientResponseByNoteId(noteId);

        trueFalseFlashcardDto.setUserId(getCurrentUserId());

        TrueFalseFlashcard trueFalseFlashcard = modelMapper.map(trueFalseFlashcardDto, TrueFalseFlashcard.class);
        trueFalseFlashcard.setType(FlashcardType.TRUE_FALSE);

        trueFalseFlashcard.setNoteId(noteClientResponse.getId());

        AbstractFlashcard newFlashcard = flashcardRepository.save(trueFalseFlashcard);

        return modelMapper.map(newFlashcard, TrueFalseFlashcardDto.class);
    }

    @Transactional
    @Override
    public AbstractFlashcardDto updateFlashcard(Long flashcardId, ShortAnswerFlashcardDto shortAnswerFlashcardDto) {

        AbstractFlashcard flashcard = flashcardRepository.findById(flashcardId).orElseThrow(() ->
                new ResourceNotFoundException("Flashcard", "id", flashcardId));

        if(flashcard.getType() != FlashcardType.SHORT_ANSWER){
            throw new FlashcardTypeException(flashcardId, flashcard.getType(), FlashcardType.SHORT_ANSWER);
        }
        ShortAnswerFlashcard shortAnswerFlashcard = (ShortAnswerFlashcard) flashcard;

        ShortAnswerFlashcard newShortAnswerFlashcard = modelMapper.map(shortAnswerFlashcardDto, ShortAnswerFlashcard.class);
        newShortAnswerFlashcard.setType(FlashcardType.SHORT_ANSWER);
        newShortAnswerFlashcard.setId(shortAnswerFlashcard.getId());
        newShortAnswerFlashcard.setNoteId(shortAnswerFlashcard.getNoteId());
        newShortAnswerFlashcard.setUserId(shortAnswerFlashcard.getUserId());

        AbstractFlashcard updatedFlashcard = flashcardRepository.save(newShortAnswerFlashcard);

        return modelMapper.map(updatedFlashcard, ShortAnswerFlashcardDto.class);
    }

    @Transactional
    @Override
    public AbstractFlashcardDto updateFlashcard(Long flashcardId, FillInTheBlankFlashcardDto fillInTheBlankFlashcardDto) {

        AbstractFlashcard flashcard = flashcardRepository.findById(flashcardId).orElseThrow(() ->
                new ResourceNotFoundException("Flashcard", "id", flashcardId));

        if(flashcard.getType() != FlashcardType.FILL_IN_THE_BLANK){
            throw new FlashcardTypeException(flashcardId, flashcard.getType(), FlashcardType.FILL_IN_THE_BLANK);
        }
        FillInTheBlankFlashcard fillInTheBlankFlashcard = (FillInTheBlankFlashcard) flashcard;

        FillInTheBlankFlashcard newFillInTheBlankFlashcard = modelMapper.map(fillInTheBlankFlashcardDto, FillInTheBlankFlashcard.class);
        newFillInTheBlankFlashcard.setType(FlashcardType.FILL_IN_THE_BLANK);
        newFillInTheBlankFlashcard.setId(fillInTheBlankFlashcard.getId());
        newFillInTheBlankFlashcard.setNoteId(fillInTheBlankFlashcard.getNoteId());
        newFillInTheBlankFlashcard.setUserId(fillInTheBlankFlashcard.getUserId());

        List<InBlankAnswer> inBlankAnswers = fillInTheBlankFlashcard.getInBlankAnswers();
        int blankAnswerSize = inBlankAnswers.size();

        if (newFillInTheBlankFlashcard.getInBlankAnswers().size() != blankAnswerSize) {
            throw new IllegalArgumentException("Number of blank answers cannot be changed");
        }

        for (int i = 0; i < blankAnswerSize; i++) {
            inBlankAnswers.get(i).setText(newFillInTheBlankFlashcard.getInBlankAnswers().get(i).getText());
            inBlankAnswers.get(i).setFlashcard(fillInTheBlankFlashcard);
        }

        newFillInTheBlankFlashcard.setInBlankAnswers(inBlankAnswers);

        AbstractFlashcard updatedFlashcard = flashcardRepository.save(newFillInTheBlankFlashcard);

        return modelMapper.map(updatedFlashcard, FillInTheBlankFlashcardDto.class);
    }

    @Transactional
    @Override
    public AbstractFlashcardDto updateFlashcard(Long flashcardId, MultipleChoiceFlashcardDto multipleChoiceFlashcardDto) {

        AbstractFlashcard flashcard = flashcardRepository.findById(flashcardId).orElseThrow(() ->
                new ResourceNotFoundException("Flashcard", "id", flashcardId));

        if(flashcard.getType() != FlashcardType.MULTIPLE_CHOICE){
            throw new FlashcardTypeException(flashcardId, flashcard.getType(), FlashcardType.MULTIPLE_CHOICE);
        }
        MultipleChoiceFlashcard multipleChoiceFlashcard = (MultipleChoiceFlashcard) flashcard;

        MultipleChoiceFlashcard newMultipleChoiceFlashcard = modelMapper.map(multipleChoiceFlashcardDto, MultipleChoiceFlashcard.class);
        newMultipleChoiceFlashcard.setType(FlashcardType.MULTIPLE_CHOICE);
        newMultipleChoiceFlashcard.setId(multipleChoiceFlashcard.getId());
        newMultipleChoiceFlashcard.setNoteId(multipleChoiceFlashcard.getNoteId());
        newMultipleChoiceFlashcard.setUserId(multipleChoiceFlashcard.getUserId());

        List<Option> options = multipleChoiceFlashcard.getOptions();
        int optionSize = options.size();

        if (newMultipleChoiceFlashcard.getOptions().size() != optionSize) {
            throw new IllegalArgumentException("Number of options cannot be changed");
        } else if (multipleChoiceFlashcardDto.getAnswerIndex() > optionSize) {
            throw new IllegalArgumentException("Answer index is out of range of options");
        }

        for (int i = 0; i < optionSize; i++) {
            options.get(i).setText(newMultipleChoiceFlashcard.getOptions().get(i).getText());
            options.get(i).setFlashcard(multipleChoiceFlashcard);
        }

        List<Option> savedOptions = optionRepository.saveAll(options);
        newMultipleChoiceFlashcard.setOptions(savedOptions);
        newMultipleChoiceFlashcard.setAnswerOption(savedOptions.get(multipleChoiceFlashcardDto.getAnswerIndex() - 1));

        AbstractFlashcard updatedFlashcard = flashcardRepository.save(newMultipleChoiceFlashcard);

        return modelMapper.map(updatedFlashcard, MultipleChoiceFlashcardDto.class);
    }

    @Override
    public AbstractFlashcardDto updateFlashcard(Long flashcardId, TrueFalseFlashcardDto trueFalseFlashcardDto) {

        AbstractFlashcard flashcard = flashcardRepository.findById(flashcardId).orElseThrow(() ->
                new ResourceNotFoundException("Flashcard", "id", flashcardId));

        if(flashcard.getType() != FlashcardType.TRUE_FALSE){
            throw new FlashcardTypeException(flashcardId, flashcard.getType(), FlashcardType.TRUE_FALSE);
        }
        TrueFalseFlashcard trueFalseFlashcard = (TrueFalseFlashcard) flashcard;

        TrueFalseFlashcard newTrueFalseFlashcard = modelMapper.map(trueFalseFlashcardDto, TrueFalseFlashcard.class);
        newTrueFalseFlashcard.setType(FlashcardType.TRUE_FALSE);
        newTrueFalseFlashcard.setId(trueFalseFlashcard.getId());
        newTrueFalseFlashcard.setNoteId(trueFalseFlashcard.getNoteId());
        newTrueFalseFlashcard.setUserId(trueFalseFlashcard.getUserId());

        AbstractFlashcard updatedFlashcard = flashcardRepository.save(newTrueFalseFlashcard);

        return modelMapper.map(updatedFlashcard, TrueFalseFlashcardDto.class);
    }

    @Override
    public void deleteFlashcardById(Long flashcardId) {

        AbstractFlashcard flashcard = flashcardRepository.findById(flashcardId).orElseThrow(() ->
                new ResourceNotFoundException("Flashcard", "id", flashcardId));

        flashcardRepository.delete(flashcard);
    }

    private Long getCurrentUserId() {
        return Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }

    private NoteClientResponse getNoteClientResponseByNoteId(Long noteId) {
        try{
            return noteServiceClient.getNoteById(noteId).getBody();
        } catch (FeignException e) {
            if (e.status() == HttpStatus.NOT_FOUND.value())
                throw new ResourceNotFoundException("Note", "id", noteId);
            else
                throw new WebAPIException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

//    private Boolean containsRole(String roleName) {
//        return getUserDetails().getAuthorities().stream().anyMatch(
//                authority -> authority.getAuthority().equals(roleName));
//    }

    private AbstractFlashcardDto mapToDto(AbstractFlashcard flashcard) {
        AbstractFlashcardDto flashcardDto;
        if (flashcard.getType() == FlashcardType.SHORT_ANSWER){
            flashcardDto = modelMapper.map(flashcard, ShortAnswerFlashcardDto.class);
        } else if (flashcard.getType() == FlashcardType.FILL_IN_THE_BLANK){
            flashcardDto = modelMapper.map(flashcard, FillInTheBlankFlashcardDto.class);
        } else if (flashcard.getType() == FlashcardType.MULTIPLE_CHOICE){
            flashcardDto = modelMapper.map(flashcard, MultipleChoiceFlashcardDto.class);
        } else if (flashcard.getType() == FlashcardType.TRUE_FALSE){
            flashcardDto = modelMapper.map(flashcard, TrueFalseFlashcardDto.class);
        } else  {
            throw new IllegalArgumentException("Flashcard type is not supported");
        }
        return flashcardDto;
    }
}

package com.vincennlin.flashcardservice.service.impl;

import com.vincennlin.flashcardservice.client.NoteServiceClient;
import com.vincennlin.flashcardservice.constant.FlashcardType;
import com.vincennlin.flashcardservice.entity.Flashcard;
import com.vincennlin.flashcardservice.entity.concrete.*;
import com.vincennlin.flashcardservice.exception.FlashcardTypeException;
import com.vincennlin.flashcardservice.exception.ResourceNotFoundException;
import com.vincennlin.flashcardservice.exception.ResourceOwnershipException;
import com.vincennlin.flashcardservice.payload.FlashcardDto;
import com.vincennlin.flashcardservice.payload.NoteClientResponse;
import com.vincennlin.flashcardservice.payload.concrete.ShortAnswerFlashcardDto;
import com.vincennlin.flashcardservice.repository.FlashcardRepository;
import com.vincennlin.flashcardservice.repository.OptionRepository;
import com.vincennlin.flashcardservice.service.FlashcardService;
import com.vincennlin.noteservice.entity.Note;
import com.vincennlin.noteservice.payload.NoteDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class FlashcardServiceImpl implements FlashcardService {

    private NoteServiceClient noteServiceClient;

    private ModelMapper modelMapper;

    private FlashcardRepository flashcardRepository;

    private OptionRepository optionRepository;
//
//    @Override
//    public List<FlashcardDto> getFlashcardsByNoteId(Long noteId) {
//
//        Note note = noteRepository.findById(noteId).orElseThrow(() ->
//                new ResourceNotFoundException("Note", "id", noteId));
//
//        authorizeOwnership(note.getUser().getId());
//
//        List<Flashcard> flashcards = note.getFlashcards();
//
//        return flashcards.stream().map(this::mapToDto).toList();
//    }
//
//    @Override
//    public FlashcardDto getFlashcardById(Long flashcardId) {
//
//        Flashcard flashcard = flashcardRepository.findById(flashcardId).orElseThrow(() ->
//                new ResourceNotFoundException("Flashcard", "id", flashcardId));
//
//        authorizeOwnership(flashcard.getUser().getId());
//
//        return mapToDto(flashcard);
//    }
//
    @Override
    public FlashcardDto createFlashcard(Long noteId, ShortAnswerFlashcardDto shortAnswerFlashcardDto) {

        NoteDto noteDto = noteServiceClient.getNoteById(noteId).getBody();

        Note note = modelMapper.map(noteDto, Note.class);

        shortAnswerFlashcardDto.setUserId(getCurrentUserId());

        ShortAnswerFlashcard shortAnswerFlashcard = modelMapper.map(shortAnswerFlashcardDto, ShortAnswerFlashcard.class);
        shortAnswerFlashcard.setType(FlashcardType.SHORT_ANSWER);

        shortAnswerFlashcard.setNote(note);

        Flashcard newFlashcard = flashcardRepository.save(shortAnswerFlashcard);

        return modelMapper.map(newFlashcard, ShortAnswerFlashcardDto.class);
    }
//
//    @Override
//    public FlashcardDto createFlashcard(Long noteId, FillInTheBlankFlashcardDto fillInTheBlankFlashcardDto) {
//
//        Note note = noteRepository.findById(noteId).orElseThrow(() ->
//                new ResourceNotFoundException("Note", "id", noteId));
//
//        authorizeOwnership(note.getUser().getId());
//        fillInTheBlankFlashcardDto.setUserId(getCurrentUserId());
//
//        FillInTheBlankFlashcard fillInTheBlankFlashcard = modelMapper.map(fillInTheBlankFlashcardDto, FillInTheBlankFlashcard.class);
//        fillInTheBlankFlashcard.setType(FlashcardType.FILL_IN_THE_BLANK);
//
//        fillInTheBlankFlashcard.setNote(note);
//
//        fillInTheBlankFlashcard.getInBlankAnswers().forEach(
//                inBlankAnswers -> inBlankAnswers.setFlashcard(fillInTheBlankFlashcard));
//
//        Flashcard newFlashcard = flashcardRepository.save(fillInTheBlankFlashcard);
//
//        return modelMapper.map(newFlashcard, FillInTheBlankFlashcardDto.class);
//    }
//
//    @Transactional
//    @Override
//    public FlashcardDto createFlashcard(Long noteId, MultipleChoiceFlashcardDto multipleChoiceFlashcardDto) {
//
//        Note note = noteRepository.findById(noteId).orElseThrow(() ->
//                new ResourceNotFoundException("Note", "id", noteId));
//
//        authorizeOwnership(note.getUser().getId());
//        multipleChoiceFlashcardDto.setUserId(getCurrentUserId());
//
//        MultipleChoiceFlashcard multipleChoiceFlashcard = modelMapper.map(multipleChoiceFlashcardDto, MultipleChoiceFlashcard.class);
//        multipleChoiceFlashcard.setType(FlashcardType.MULTIPLE_CHOICE);
//        multipleChoiceFlashcard.setNote(note);
//
//        List<Option> options = multipleChoiceFlashcard.getOptions();
//        options.forEach(option -> option.setFlashcard(multipleChoiceFlashcard));
//        List<Option> savedOptions = optionRepository.saveAll(options);
//
//        if (multipleChoiceFlashcardDto.getAnswerIndex() > savedOptions.size()) {
//            throw new IllegalArgumentException("Answer index is out of range of options");
//        }
//        multipleChoiceFlashcard.setAnswerOption(savedOptions.get(multipleChoiceFlashcardDto.getAnswerIndex() - 1));
//
//        Flashcard newFlashcard = flashcardRepository.save(multipleChoiceFlashcard);
//
//        return modelMapper.map(newFlashcard, MultipleChoiceFlashcardDto.class);
//    }
//
//    @Override
//    public FlashcardDto createFlashcard(Long noteId, TrueFalseFlashcardDto trueFalseFlashcardDto) {
//
//        Note note = noteRepository.findById(noteId).orElseThrow(() ->
//                new ResourceNotFoundException("Note", "id", noteId));
//
//        authorizeOwnership(note.getUser().getId());
//        trueFalseFlashcardDto.setUserId(getCurrentUserId());
//
//        TrueFalseFlashcard trueFalseFlashcard = modelMapper.map(trueFalseFlashcardDto, TrueFalseFlashcard.class);
//        trueFalseFlashcard.setType(FlashcardType.TRUE_FALSE);
//
//        trueFalseFlashcard.setNote(note);
//
//        Flashcard newFlashcard = flashcardRepository.save(trueFalseFlashcard);
//
//        return modelMapper.map(newFlashcard, TrueFalseFlashcardDto.class);
//    }
//
//    @Transactional
//    @Override
//    public FlashcardDto updateFlashcard(Long flashcardId, ShortAnswerFlashcardDto shortAnswerFlashcardDto) {
//
//        Flashcard flashcard = flashcardRepository.findById(flashcardId).orElseThrow(() ->
//                new ResourceNotFoundException("Flashcard", "id", flashcardId));
//
//        authorizeOwnership(flashcard.getUser().getId());
//
//        if(flashcard.getType() != FlashcardType.SHORT_ANSWER){
//            throw new FlashcardTypeException(flashcardId, flashcard.getType(), FlashcardType.SHORT_ANSWER);
//        }
//        ShortAnswerFlashcard shortAnswerFlashcard = (ShortAnswerFlashcard) flashcard;
//
//        ShortAnswerFlashcard newShortAnswerFlashcard = modelMapper.map(shortAnswerFlashcardDto, ShortAnswerFlashcard.class);
//        newShortAnswerFlashcard.setType(FlashcardType.SHORT_ANSWER);
//        newShortAnswerFlashcard.setNote(shortAnswerFlashcard.getNote());
//        newShortAnswerFlashcard.setId(shortAnswerFlashcard.getId());
//        newShortAnswerFlashcard.setUser(shortAnswerFlashcard.getUser());
//
//        Flashcard updatedFlashcard = flashcardRepository.save(newShortAnswerFlashcard);
//
//        return modelMapper.map(updatedFlashcard, ShortAnswerFlashcardDto.class);
//    }
//
//    @Transactional
//    @Override
//    public FlashcardDto updateFlashcard(Long flashcardId, FillInTheBlankFlashcardDto fillInTheBlankFlashcardDto) {
//
//        Flashcard flashcard = flashcardRepository.findById(flashcardId).orElseThrow(() ->
//                new ResourceNotFoundException("Flashcard", "id", flashcardId));
//
//        authorizeOwnership(flashcard.getUser().getId());
//
//        if(flashcard.getType() != FlashcardType.FILL_IN_THE_BLANK){
//            throw new FlashcardTypeException(flashcardId, flashcard.getType(), FlashcardType.FILL_IN_THE_BLANK);
//        }
//        FillInTheBlankFlashcard fillInTheBlankFlashcard = (FillInTheBlankFlashcard) flashcard;
//
//        FillInTheBlankFlashcard newFillInTheBlankFlashcard = modelMapper.map(fillInTheBlankFlashcardDto, FillInTheBlankFlashcard.class);
//        newFillInTheBlankFlashcard.setType(FlashcardType.FILL_IN_THE_BLANK);
//        newFillInTheBlankFlashcard.setNote(fillInTheBlankFlashcard.getNote());
//        newFillInTheBlankFlashcard.setId(fillInTheBlankFlashcard.getId());
//        newFillInTheBlankFlashcard.setUser(fillInTheBlankFlashcard.getUser());
//
//        List<InBlankAnswer> inBlankAnswers = fillInTheBlankFlashcard.getInBlankAnswers();
//        int blankAnswerSize = inBlankAnswers.size();
//
//        if (newFillInTheBlankFlashcard.getInBlankAnswers().size() != blankAnswerSize) {
//            throw new IllegalArgumentException("Number of blank answers cannot be changed");
//        }
//
//        for (int i = 0; i < blankAnswerSize; i++) {
//            inBlankAnswers.get(i).setText(newFillInTheBlankFlashcard.getInBlankAnswers().get(i).getText());
//            inBlankAnswers.get(i).setFlashcard(fillInTheBlankFlashcard);
//        }
//
//        newFillInTheBlankFlashcard.setInBlankAnswers(inBlankAnswers);
//
//        Flashcard updatedFlashcard = flashcardRepository.save(newFillInTheBlankFlashcard);
//
//        return modelMapper.map(updatedFlashcard, FillInTheBlankFlashcardDto.class);
//    }
//
//    @Transactional
//    @Override
//    public FlashcardDto updateFlashcard(Long flashcardId, MultipleChoiceFlashcardDto multipleChoiceFlashcardDto) {
//
//        Flashcard flashcard = flashcardRepository.findById(flashcardId).orElseThrow(() ->
//                new ResourceNotFoundException("Flashcard", "id", flashcardId));
//
//        authorizeOwnership(flashcard.getUser().getId());
//
//        if(flashcard.getType() != FlashcardType.MULTIPLE_CHOICE){
//            throw new FlashcardTypeException(flashcardId, flashcard.getType(), FlashcardType.MULTIPLE_CHOICE);
//        }
//        MultipleChoiceFlashcard multipleChoiceFlashcard = (MultipleChoiceFlashcard) flashcard;
//
//        MultipleChoiceFlashcard newMultipleChoiceFlashcard = modelMapper.map(multipleChoiceFlashcardDto, MultipleChoiceFlashcard.class);
//        newMultipleChoiceFlashcard.setType(FlashcardType.MULTIPLE_CHOICE);
//        newMultipleChoiceFlashcard.setNote(multipleChoiceFlashcard.getNote());
//        newMultipleChoiceFlashcard.setId(multipleChoiceFlashcard.getId());
//        newMultipleChoiceFlashcard.setUser(multipleChoiceFlashcard.getUser());
//
//        List<Option> options = multipleChoiceFlashcard.getOptions();
//        int optionSize = options.size();
//
//        if (newMultipleChoiceFlashcard.getOptions().size() != optionSize) {
//            throw new IllegalArgumentException("Number of options cannot be changed");
//        } else if (multipleChoiceFlashcardDto.getAnswerIndex() > optionSize) {
//            throw new IllegalArgumentException("Answer index is out of range of options");
//        }
//
//        for (int i = 0; i < optionSize; i++) {
//            options.get(i).setText(newMultipleChoiceFlashcard.getOptions().get(i).getText());
//            options.get(i).setFlashcard(multipleChoiceFlashcard);
//        }
//
//        List<Option> savedOptions = optionRepository.saveAll(options);
//        newMultipleChoiceFlashcard.setOptions(savedOptions);
//        newMultipleChoiceFlashcard.setAnswerOption(savedOptions.get(multipleChoiceFlashcardDto.getAnswerIndex() - 1));
//
//        Flashcard updatedFlashcard = flashcardRepository.save(newMultipleChoiceFlashcard);
//
//        return modelMapper.map(updatedFlashcard, MultipleChoiceFlashcardDto.class);
//    }
//
//    @Override
//    public FlashcardDto updateFlashcard(Long flashcardId, TrueFalseFlashcardDto trueFalseFlashcardDto) {
//
//        Flashcard flashcard = flashcardRepository.findById(flashcardId).orElseThrow(() ->
//                new ResourceNotFoundException("Flashcard", "id", flashcardId));
//
//        authorizeOwnership(flashcard.getUser().getId());
//
//        if(flashcard.getType() != FlashcardType.TRUE_FALSE){
//            throw new FlashcardTypeException(flashcardId, flashcard.getType(), FlashcardType.TRUE_FALSE);
//        }
//        TrueFalseFlashcard trueFalseFlashcard = (TrueFalseFlashcard) flashcard;
//
//        TrueFalseFlashcard newTrueFalseFlashcard = modelMapper.map(trueFalseFlashcardDto, TrueFalseFlashcard.class);
//        newTrueFalseFlashcard.setType(FlashcardType.TRUE_FALSE);
//        newTrueFalseFlashcard.setNote(trueFalseFlashcard.getNote());
//        newTrueFalseFlashcard.setId(trueFalseFlashcard.getId());
//        newTrueFalseFlashcard.setUser(trueFalseFlashcard.getUser());
//
//        Flashcard updatedFlashcard = flashcardRepository.save(newTrueFalseFlashcard);
//
//        return modelMapper.map(updatedFlashcard, TrueFalseFlashcardDto.class);
//    }
//
//    @Override
//    public void deleteFlashcardById(Long flashcardId) {
//
//        Flashcard flashcard = flashcardRepository.findById(flashcardId).orElseThrow(() ->
//                new ResourceNotFoundException("Flashcard", "id", flashcardId));
//
//        authorizeOwnership(flashcard.getUser().getId());
//
//        flashcardRepository.delete(flashcard);
//    }

    private Long getCurrentUserId() {
        return Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }

//    private Boolean containsRole(String roleName) {
//        return getUserDetails().getAuthorities().stream().anyMatch(
//                authority -> authority.getAuthority().equals(roleName));
//    }
//
//    private FlashcardDto mapToDto(Flashcard flashcard) {
//        FlashcardDto flashcardDto;
//        if (flashcard.getType() == FlashcardType.SHORT_ANSWER){
//            flashcardDto = modelMapper.map(flashcard, ShortAnswerFlashcardDto.class);
//        } else if (flashcard.getType() == FlashcardType.FILL_IN_THE_BLANK){
//            flashcardDto = modelMapper.map(flashcard, FillInTheBlankFlashcardDto.class);
//        } else if (flashcard.getType() == FlashcardType.MULTIPLE_CHOICE){
//            flashcardDto = modelMapper.map(flashcard, MultipleChoiceFlashcardDto.class);
//        } else if (flashcard.getType() == FlashcardType.TRUE_FALSE){
//            flashcardDto = modelMapper.map(flashcard, TrueFalseFlashcardDto.class);
//        } else  {
//            throw new IllegalArgumentException("Flashcard type is not supported");
//        }
//        return flashcardDto;
//    }
}

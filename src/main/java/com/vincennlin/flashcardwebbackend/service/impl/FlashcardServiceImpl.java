package com.vincennlin.flashcardwebbackend.service.impl;

import com.vincennlin.flashcardwebbackend.constant.FlashcardType;
import com.vincennlin.flashcardwebbackend.entity.flashcard.BlankAnswer;
import com.vincennlin.flashcardwebbackend.entity.flashcard.Flashcard;
import com.vincennlin.flashcardwebbackend.entity.Note;
import com.vincennlin.flashcardwebbackend.entity.flashcard.Option;
import com.vincennlin.flashcardwebbackend.entity.flashcard.concrete.FillInTheBlankFlashcard;
import com.vincennlin.flashcardwebbackend.entity.flashcard.concrete.MultipleChoiceFlashcard;
import com.vincennlin.flashcardwebbackend.entity.flashcard.concrete.ShortAnswerFlashcard;
import com.vincennlin.flashcardwebbackend.entity.flashcard.concrete.TrueFalseFlashcard;
import com.vincennlin.flashcardwebbackend.exception.ResourceNotFoundException;
import com.vincennlin.flashcardwebbackend.payload.flashcard.FlashcardDto;
import com.vincennlin.flashcardwebbackend.payload.flashcard.concrete.*;
import com.vincennlin.flashcardwebbackend.repository.FlashcardRepository;
import com.vincennlin.flashcardwebbackend.repository.NoteRepository;
import com.vincennlin.flashcardwebbackend.repository.OptionRepository;
import com.vincennlin.flashcardwebbackend.service.FlashcardService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class FlashcardServiceImpl implements FlashcardService {

    private FlashcardRepository flashcardRepository;

    private ModelMapper modelMapper;

    private NoteRepository noteRepository;

    private OptionRepository optionRepository;

    @Override
    public List<FlashcardDto> getFlashcardsByNoteId(Long noteId) {

        Note note = noteRepository.findById(noteId).orElseThrow(() ->
                new ResourceNotFoundException("Note", "id", noteId));

        List<Flashcard> flashcards = note.getFlashcards();

        return flashcards.stream().map(this::mapToDto).toList();
    }

    @Override
    public FlashcardDto getFlashcardById(Long flashcardId) {

        Flashcard flashcard = flashcardRepository.findById(flashcardId).orElseThrow(() ->
                new ResourceNotFoundException("Flashcard", "id", flashcardId));

        return mapToDto(flashcard);
    }

    @Override
    public FlashcardDto createFlashcard(Long noteId, ShortAnswerFlashcardDto shortAnswerFlashcardDto) {

        Note note = noteRepository.findById(noteId).orElseThrow(() ->
                new ResourceNotFoundException("Note", "id", noteId));

        ShortAnswerFlashcard shortAnswerFlashcard = modelMapper.map(shortAnswerFlashcardDto, ShortAnswerFlashcard.class);
        shortAnswerFlashcard.setType(FlashcardType.SHORT_ANSWER);

        shortAnswerFlashcard.setNote(note);

        Flashcard newFlashcard = flashcardRepository.save(shortAnswerFlashcard);

        return modelMapper.map(newFlashcard, ShortAnswerFlashcardDto.class);
    }

    @Override
    public FlashcardDto createFlashcard(Long noteId, FillInTheBlankFlashcardDto fillInTheBlankFlashcardDto) {

        Note note = noteRepository.findById(noteId).orElseThrow(() ->
                new ResourceNotFoundException("Note", "id", noteId));

        FillInTheBlankFlashcard fillInTheBlankFlashcard = modelMapper.map(fillInTheBlankFlashcardDto, FillInTheBlankFlashcard.class);
        fillInTheBlankFlashcard.setType(FlashcardType.FILL_IN_THE_BLANK);

        fillInTheBlankFlashcard.setNote(note);

        fillInTheBlankFlashcard.getBlankAnswers().forEach(
                blankAnswer -> blankAnswer.setFlashcard(fillInTheBlankFlashcard));

        Flashcard newFlashcard = flashcardRepository.save(fillInTheBlankFlashcard);

        return modelMapper.map(newFlashcard, FillInTheBlankFlashcardDto.class);
    }

    @Transactional
    @Override
    public FlashcardDto createFlashcard(Long noteId, MultipleChoiceFlashcardDto multipleChoiceFlashcardDto) {

        Note note = noteRepository.findById(noteId).orElseThrow(() ->
                new ResourceNotFoundException("Note", "id", noteId));

        MultipleChoiceFlashcard multipleChoiceFlashcard = modelMapper.map(multipleChoiceFlashcardDto, MultipleChoiceFlashcard.class);
        multipleChoiceFlashcard.setType(FlashcardType.MULTIPLE_CHOICE);
        multipleChoiceFlashcard.setNote(note);

        List<Option> options = multipleChoiceFlashcard.getOptions();
        options.forEach(option -> option.setFlashcard(multipleChoiceFlashcard));
        List<Option> savedOptions = optionRepository.saveAll(options);

        if (multipleChoiceFlashcardDto.getAnswerIndex() > savedOptions.size()) {
            throw new IllegalArgumentException("Answer index is out of range of options");
        }
        multipleChoiceFlashcard.setAnswer(savedOptions.get(multipleChoiceFlashcardDto.getAnswerIndex() - 1));

        Flashcard newFlashcard = flashcardRepository.save(multipleChoiceFlashcard);

        return modelMapper.map(newFlashcard, MultipleChoiceFlashcardDto.class);
    }

    @Override
    public FlashcardDto createFlashcard(Long noteId, TrueFalseFlashcardDto trueFalseFlashcardDto) {

        Note note = noteRepository.findById(noteId).orElseThrow(() ->
                new ResourceNotFoundException("Note", "id", noteId));

        TrueFalseFlashcard trueFalseFlashcard = modelMapper.map(trueFalseFlashcardDto, TrueFalseFlashcard.class);
        trueFalseFlashcard.setType(FlashcardType.TRUE_FALSE);

        trueFalseFlashcard.setNote(note);

        Flashcard newFlashcard = flashcardRepository.save(trueFalseFlashcard);

        return modelMapper.map(newFlashcard, TrueFalseFlashcardDto.class);
    }

    @Override
    public FlashcardDto updateFlashcard(Long flashcardId, FlashcardDto flashcardDto) {

        Flashcard flashcard = flashcardRepository.findById(flashcardId).orElseThrow(() ->
                new ResourceNotFoundException("Flashcard", "id", flashcardId));

        flashcard.setQuestion(flashcardDto.getQuestion());
//        flashcard.setAnswer(flashcardDto.getAnswer());
        flashcard.setExtraInfo(flashcardDto.getExtraInfo());

        Flashcard updatedFlashcard = flashcardRepository.save(flashcard);

        return modelMapper.map(updatedFlashcard, FlashcardDto.class);
    }

    @Override
    public void deleteFlashcardById(Long flashcardId) {

        Flashcard flashcard = flashcardRepository.findById(flashcardId).orElseThrow(() ->
                new ResourceNotFoundException("Flashcard", "id", flashcardId));

        flashcardRepository.delete(flashcard);
    }

    private FlashcardDto mapToDto(Flashcard flashcard) {
        FlashcardDto flashcardDto = null;
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

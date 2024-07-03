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
import com.vincennlin.flashcardwebbackend.service.FlashcardService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlashcardServiceImpl implements FlashcardService {

    private FlashcardRepository flashcardRepository;

    private ModelMapper modelMapper;

    private NoteRepository noteRepository;

    public FlashcardServiceImpl(FlashcardRepository flashcardRepository,
                                ModelMapper modelMapper,
                                NoteRepository noteRepository) {
        this.flashcardRepository = flashcardRepository;
        this.modelMapper = modelMapper;
        this.noteRepository = noteRepository;
    }

    @Override
    public List<FlashcardDto> getFlashcardsByNoteId(Long noteId) {

        Note note = noteRepository.findById(noteId).orElseThrow(() ->
                new ResourceNotFoundException("Note", "id", noteId));

        List<Flashcard> flashcards = note.getFlashcards();

        return flashcards.stream()
                .map(flashcard -> modelMapper.map(flashcard, FlashcardDto.class)).toList();
    }

    @Override
    public FlashcardDto getFlashcardById(Long flashcardId) {

        Flashcard flashcard = flashcardRepository.findById(flashcardId).orElseThrow(() ->
                new ResourceNotFoundException("Flashcard", "id", flashcardId));

        return modelMapper.map(flashcard, FlashcardDto.class);
    }

    @Override
    public FlashcardDto createFlashcard(Long noteId, FlashcardDto flashcardDto) {

        Note note = noteRepository.findById(noteId).orElseThrow(() ->
                new ResourceNotFoundException("Note", "id", noteId));

        Flashcard flashcard = mapToEntity(flashcardDto);
        addRelation(flashcard);

        flashcard.setNote(note);

        Flashcard newFlashcard = flashcardRepository.save(flashcard);

        return mapToDto(newFlashcard);
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

    private Flashcard mapToEntity(FlashcardDto flashcardDto) {
        Flashcard flashcard = null;
        if (flashcardDto instanceof FillInTheBlankFlashcardDto) {
            flashcard = modelMapper.map(flashcardDto, FillInTheBlankFlashcard.class);
            flashcard.setType(FlashcardType.FILL_IN_THE_BLANK);
        } else if (flashcardDto instanceof MultipleChoiceFlashcardDto multipleChoiceFlashcardDto) {
            MultipleChoiceFlashcard multipleChoiceFlashcard = modelMapper.map(multipleChoiceFlashcardDto, MultipleChoiceFlashcard.class);
            if (multipleChoiceFlashcardDto.getAnswerIndex() > multipleChoiceFlashcardDto.getOptions().size()) {
                throw new IllegalArgumentException("Answer index is out of range of options");
            }
            OptionDto answer = multipleChoiceFlashcardDto.getOptions().get(multipleChoiceFlashcardDto.getAnswerIndex());
            multipleChoiceFlashcard.setAnswer(modelMapper.map(answer, Option.class));
            multipleChoiceFlashcard.setType(FlashcardType.MULTIPLE_CHOICE);
            return multipleChoiceFlashcard;
        } else if (flashcardDto instanceof ShortAnswerFlashcardDto) {
            flashcard = modelMapper.map(flashcardDto, ShortAnswerFlashcard.class);
            flashcard.setType(FlashcardType.SHORT_ANSWER);
        } else if (flashcardDto instanceof TrueFalseFlashcardDto) {
            flashcard = modelMapper.map(flashcardDto, TrueFalseFlashcard.class);
            flashcard.setType(FlashcardType.TRUE_FALSE);
        }
        return flashcard;
    }

    private FlashcardDto mapToDto(Flashcard flashcard) {
        if (flashcard instanceof FillInTheBlankFlashcard) {
            return modelMapper.map(flashcard, FillInTheBlankFlashcardDto.class);
        } else if (flashcard instanceof MultipleChoiceFlashcard) {
            return modelMapper.map(flashcard, MultipleChoiceFlashcardDto.class);
        } else if (flashcard instanceof ShortAnswerFlashcard) {
            return modelMapper.map(flashcard, ShortAnswerFlashcardDto.class);
        } else if (flashcard instanceof TrueFalseFlashcard) {
            return modelMapper.map(flashcard, TrueFalseFlashcardDto.class);
        } else {
            throw new IllegalArgumentException("Invalid flashcard type");
        }
    }

    private void addRelation(Flashcard flashcard) {
        if (flashcard instanceof FillInTheBlankFlashcard fillInTheBlankFlashcard) {
            fillInTheBlankFlashcard.getBlankAnswers().forEach(
                    blankAnswer -> blankAnswer.setFlashcard(fillInTheBlankFlashcard));
        } else if (flashcard instanceof MultipleChoiceFlashcard multipleChoiceFlashcard) {
            multipleChoiceFlashcard.getOptions().forEach(
                    option -> option.setFlashcard(multipleChoiceFlashcard));
            multipleChoiceFlashcard.getAnswer().setFlashcard(multipleChoiceFlashcard);
        }
    }
}

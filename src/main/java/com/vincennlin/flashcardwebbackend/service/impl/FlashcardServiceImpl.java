package com.vincennlin.flashcardwebbackend.service.impl;

import com.vincennlin.flashcardwebbackend.entity.Flashcard;
import com.vincennlin.flashcardwebbackend.entity.Note;
import com.vincennlin.flashcardwebbackend.exception.ResourceNotFoundException;
import com.vincennlin.flashcardwebbackend.payload.FlashcardDto;
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

        Flashcard flashcard = modelMapper.map(flashcardDto, Flashcard.class);

        flashcard.setNote(note);

        Flashcard newFlashcard = flashcardRepository.save(flashcard);

        return modelMapper.map(newFlashcard, FlashcardDto.class);
    }

    @Override
    public FlashcardDto updateFlashcard(Long flashcardId, FlashcardDto flashcardDto) {

        Flashcard flashcard = flashcardRepository.findById(flashcardId).orElseThrow(() ->
                new ResourceNotFoundException("Flashcard", "id", flashcardId));

        flashcard.setQuestion(flashcardDto.getQuestion());
        flashcard.setAnswer(flashcardDto.getAnswer());
        flashcard.setExtraInfo(flashcardDto.getExtraInfo());

        Flashcard updatedFlashcard = flashcardRepository.save(flashcard);

        return modelMapper.map(updatedFlashcard, FlashcardDto.class);
    }
}

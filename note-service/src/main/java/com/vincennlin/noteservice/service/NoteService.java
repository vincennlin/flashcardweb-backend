package com.vincennlin.noteservice.service;


import com.vincennlin.noteservice.payload.flashcard.dto.AbstractFlashcardDto;
import com.vincennlin.noteservice.payload.note.dto.NoteDto;
import com.vincennlin.noteservice.payload.note.page.NotePageResponse;
import com.vincennlin.noteservice.payload.request.GenerateFlashcardRequest;
import com.vincennlin.noteservice.payload.request.GenerateFlashcardsRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NoteService {

    NotePageResponse getAllNotes(Pageable pageable);

    NotePageResponse getNotesByUserId(Long userId, Pageable pageable);

    NoteDto getNoteById(Long noteId);

    NoteDto createNote(NoteDto noteDto);

    NoteDto updateNote(Long noteId, NoteDto noteDto);

    void deleteNoteById(Long noteId);

    Boolean isNoteOwner(Long noteId);

    AbstractFlashcardDto generateFlashcard(Long noteId, GenerateFlashcardRequest request);

    List<AbstractFlashcardDto> generateFlashcards(Long noteId, GenerateFlashcardsRequest request);
}

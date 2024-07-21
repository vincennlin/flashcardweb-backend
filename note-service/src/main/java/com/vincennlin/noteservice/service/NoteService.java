package com.vincennlin.noteservice.service;


import com.vincennlin.noteservice.payload.flashcard.dto.AbstractFlashcardDto;
import com.vincennlin.noteservice.payload.flashcard.type.FlashcardType;
import com.vincennlin.noteservice.payload.note.FlashcardTypeQuantity;
import com.vincennlin.noteservice.payload.note.NoteDto;
import com.vincennlin.noteservice.payload.note.NotePageResponse;
import org.springframework.data.domain.Pageable;

public interface NoteService {

    NotePageResponse getAllNotes(Pageable pageable);

    NotePageResponse getNotesByUserId(Long userId, Pageable pageable);

    NoteDto getNoteById(Long noteId);

    NoteDto createNote(NoteDto noteDto);

    NoteDto updateNote(Long noteId, NoteDto noteDto);

    void deleteNoteById(Long noteId);

    Boolean isNoteOwner(Long noteId);

    AbstractFlashcardDto generateFlashcard(Long noteId, FlashcardType flashcardType);
}

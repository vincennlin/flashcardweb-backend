package com.vincennlin.flashcardwebbackend.service;

import com.vincennlin.flashcardwebbackend.payload.note.NoteDto;
import com.vincennlin.flashcardwebbackend.payload.note.NotePageResponse;
import org.springframework.data.domain.Pageable;

public interface NoteService {

    NotePageResponse getAllNotes(Pageable pageable);

    NoteDto getNoteById(Long noteId);

    NoteDto createNote(NoteDto noteDto);

    NoteDto updateNote(Long noteId, NoteDto noteDto);

    void deleteNoteById(Long noteId);
}

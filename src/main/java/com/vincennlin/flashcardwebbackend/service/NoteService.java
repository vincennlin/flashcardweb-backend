package com.vincennlin.flashcardwebbackend.service;

import com.vincennlin.flashcardwebbackend.payload.NoteDto;
import com.vincennlin.flashcardwebbackend.payload.NotePageResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NoteService {

    NotePageResponse getAllNotes(Pageable pageable);

    NoteDto getNoteById(Long noteId);

    NoteDto createNote(NoteDto noteDto);

    NoteDto updateNote(Long noteId, NoteDto noteDto);

    void deleteNoteById(Long noteId);
}

package com.vincennlin.flashcardwebbackend.service;

import com.vincennlin.flashcardwebbackend.payload.NoteDto;

import java.util.List;

public interface NoteService {

    List<NoteDto> getAllNotes();

    NoteDto getNoteById(Long noteId);

    NoteDto createNote(NoteDto noteDto);

    NoteDto updateNote(Long noteId, NoteDto noteDto);

    void deleteNoteById(Long noteId);
}

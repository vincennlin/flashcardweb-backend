package com.vincennlin.flashcardbackend.service;

import com.vincennlin.flashcardbackend.payload.NoteDto;

import java.util.List;

public interface NoteService {

    List<NoteDto> getAllNotes();

    NoteDto getNoteById(Long noteId);

    NoteDto createNote(NoteDto noteDto);

    NoteDto updateNote(Long noteId, NoteDto noteDto);
}

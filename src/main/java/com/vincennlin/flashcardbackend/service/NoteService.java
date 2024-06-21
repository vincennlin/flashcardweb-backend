package com.vincennlin.flashcardbackend.service;

import com.vincennlin.flashcardbackend.payload.NoteDto;

public interface NoteService {

    NoteDto createNote(NoteDto noteDto);
}

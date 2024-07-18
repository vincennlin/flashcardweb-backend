package com.vincennlin.noteservice.service;


import com.vincennlin.noteservice.payload.NoteDto;
import com.vincennlin.noteservice.payload.NotePageResponse;
import org.springframework.data.domain.Pageable;

public interface NoteService {

    NotePageResponse getAllNotes(Pageable pageable);

    NotePageResponse getNotesByUserId(Long userId, Pageable pageable);

    NoteDto getNoteById(Long noteId);

    Boolean isNoteOwner(Long noteId);

    NoteDto createNote(NoteDto noteDto);

    NoteDto updateNote(Long noteId, NoteDto noteDto);

    void deleteNoteById(Long noteId);
}

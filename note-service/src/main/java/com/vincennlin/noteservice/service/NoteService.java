package com.vincennlin.noteservice.service;


import com.vincennlin.noteservice.payload.extract.ExtractLanguage;
import com.vincennlin.noteservice.payload.flashcard.dto.FlashcardDto;
import com.vincennlin.noteservice.payload.note.dto.NoteDto;
import com.vincennlin.noteservice.payload.note.page.NotePageResponse;
import com.vincennlin.noteservice.payload.flashcard.request.GenerateFlashcardRequest;
import com.vincennlin.noteservice.payload.flashcard.request.GenerateFlashcardsRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface NoteService {

    NotePageResponse getAllNotes(Pageable pageable);

    NotePageResponse getNotesByUserId(Long userId, Pageable pageable);

    NotePageResponse getNotesByDeckId(Long deckId, Pageable pageable);

    NoteDto getNoteById(Long noteId);

    NoteDto createNote(Long deckId, NoteDto noteDto);

    NoteDto createNoteFromPdf(Long deckId, MultipartFile pdfFile);

    NoteDto createNoteFromImage(Long deckId, ExtractLanguage language, MultipartFile imageFile);

    NoteDto updateNote(Long noteId, NoteDto noteDto);

    void deleteNoteById(Long noteId);

    Boolean isNoteOwner(Long noteId);

    FlashcardDto generateFlashcard(Long noteId, GenerateFlashcardRequest request);

    List<FlashcardDto> generateFlashcards(Long noteId, GenerateFlashcardsRequest request);
}

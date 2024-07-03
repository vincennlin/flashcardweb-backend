package com.vincennlin.flashcardwebbackend.service;

import com.vincennlin.flashcardwebbackend.payload.flashcard.FlashcardDto;

import java.util.List;

public interface FlashcardService {

    List<FlashcardDto> getFlashcardsByNoteId(Long noteId);

    FlashcardDto getFlashcardById(Long flashcardId);

    FlashcardDto createFlashcard(Long noteId, FlashcardDto flashcardDto);

    FlashcardDto updateFlashcard(Long flashcardId, FlashcardDto flashcardDto);

    void deleteFlashcardById(Long flashcardId);
}

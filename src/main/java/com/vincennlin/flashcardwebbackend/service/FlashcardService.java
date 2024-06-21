package com.vincennlin.flashcardwebbackend.service;

import com.vincennlin.flashcardwebbackend.payload.FlashcardDto;

public interface FlashcardService {

    FlashcardDto createFlashcard(Long noteId, FlashcardDto flashcardDto);
}

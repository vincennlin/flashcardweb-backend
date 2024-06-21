package com.vincennlin.flashcardwebbackend.service;

import com.vincennlin.flashcardwebbackend.payload.FlashcardDto;

import java.util.List;

public interface FlashcardService {

    List<FlashcardDto> getFlashcardsByNoteId(Long noteId);

    FlashcardDto getFlashcardById(Long flashcardId);

    FlashcardDto createFlashcard(Long noteId, FlashcardDto flashcardDto);
}

package com.vincennlin.flashcardservice.service;

import com.vincennlin.flashcardservice.payload.flashcard.dto.AbstractFlashcardDto;

import java.util.List;

public interface FlashcardService {

    List<AbstractFlashcardDto> getFlashcardsByNoteId(Long noteId);

    AbstractFlashcardDto getFlashcardById(Long flashcardId);

    AbstractFlashcardDto createFlashcard(Long noteId, AbstractFlashcardDto flashcardDto);

    List<AbstractFlashcardDto> createFlashcards(Long noteId, List<AbstractFlashcardDto> flashcardDtoList);

    AbstractFlashcardDto updateFlashcard(Long flashcardId, AbstractFlashcardDto flashcardDto);

    void deleteFlashcardById(Long flashcardId);

    void deleteFlashcardsByNoteId(Long noteId);
}

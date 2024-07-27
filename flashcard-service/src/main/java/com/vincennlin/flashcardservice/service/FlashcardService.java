package com.vincennlin.flashcardservice.service;

import com.vincennlin.flashcardservice.entity.flashcard.Flashcard;
import com.vincennlin.flashcardservice.payload.flashcard.dto.FlashcardDto;

import java.util.List;

public interface FlashcardService {

    List<FlashcardDto> getFlashcardsByNoteId(Long noteId);

    FlashcardDto getFlashcardById(Long flashcardId);

    Flashcard getFlashcardEntityById(Long flashcardId);

    List<FlashcardDto> getFlashcardsByTagNames(List<String> tagNames);

    FlashcardDto createFlashcard(Long noteId, FlashcardDto flashcardDto);

    List<FlashcardDto> createFlashcards(Long noteId, List<FlashcardDto> flashcardDtoList);

    FlashcardDto updateFlashcard(Long flashcardId, FlashcardDto flashcardDto);

    void deleteFlashcardById(Long flashcardId);

    void deleteFlashcardsByNoteId(Long noteId);

    Long getCurrentUserId();

    FlashcardDto mapToDto(Flashcard flashcard);
}

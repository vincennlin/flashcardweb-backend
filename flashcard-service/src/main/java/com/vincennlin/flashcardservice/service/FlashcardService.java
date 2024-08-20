package com.vincennlin.flashcardservice.service;

import com.vincennlin.flashcardservice.entity.flashcard.Flashcard;
import com.vincennlin.flashcardservice.payload.deck.FlashcardCountInfo;
import com.vincennlin.flashcardservice.payload.flashcard.dto.FlashcardDto;
import com.vincennlin.flashcardservice.payload.flashcard.page.FlashcardPageResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FlashcardService {

    FlashcardPageResponse getFlashcardsByDeckId(Long deckId, Pageable pageable);

    FlashcardPageResponse getFlashcardsByNoteId(Long noteId, Pageable pageable);

    FlashcardDto getFlashcardById(Long flashcardId);

    Flashcard getFlashcardEntityById(Long flashcardId);

    FlashcardPageResponse getFlashcardsByTagNames(List<String> tagNames, Pageable pageable);

    FlashcardCountInfo getFlashcardCountInfo();

    FlashcardPageResponse findFlashcardsByDeckIdAndKeyword(Long deckId, String keyword, Pageable pageable);

    FlashcardPageResponse findFlashcardsByKeyword(String keyword, Pageable pageable);

    FlashcardDto createFlashcard(Long noteId, FlashcardDto flashcardDto);

    List<FlashcardDto> createFlashcards(Long noteId, List<FlashcardDto> flashcardDtoList);

    FlashcardDto updateFlashcard(Long flashcardId, FlashcardDto flashcardDto);

    void deleteFlashcardById(Long flashcardId);

    void deleteFlashcardsByNoteId(Long noteId);

    Long getCurrentUserId();
}

package com.vincennlin.flashcardservice.service;

import com.vincennlin.flashcardservice.entity.flashcard.AbstractFlashcard;
import com.vincennlin.flashcardservice.entity.tag.Tag;
import com.vincennlin.flashcardservice.payload.flashcard.dto.AbstractFlashcardDto;

import java.util.List;

public interface FlashcardService {

    List<AbstractFlashcardDto> getFlashcardsByNoteId(Long noteId);

    AbstractFlashcardDto getFlashcardById(Long flashcardId);

    AbstractFlashcard getFlashcardEntityById(Long flashcardId);

    List<AbstractFlashcardDto> getFlashcardsByTags(List<Tag> tags);

    List<AbstractFlashcard> getFlashcardsEntitiesByTags(List<Tag> tags);

    AbstractFlashcardDto createFlashcard(Long noteId, AbstractFlashcardDto flashcardDto);

    List<AbstractFlashcardDto> createFlashcards(Long noteId, List<AbstractFlashcardDto> flashcardDtoList);

    AbstractFlashcardDto updateFlashcard(Long flashcardId, AbstractFlashcardDto flashcardDto);

    void deleteFlashcardById(Long flashcardId);

    void deleteFlashcardsByNoteId(Long noteId);
}

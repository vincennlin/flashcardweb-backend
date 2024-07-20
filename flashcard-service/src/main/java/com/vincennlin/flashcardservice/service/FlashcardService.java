package com.vincennlin.flashcardservice.service;

import com.vincennlin.flashcardservice.payload.flashcard.dto.AbstractFlashcardDto;
import com.vincennlin.flashcardservice.payload.flashcard.dto.impl.FillInTheBlankFlashcardDto;
import com.vincennlin.flashcardservice.payload.flashcard.dto.impl.MultipleChoiceFlashcardDto;
import com.vincennlin.flashcardservice.payload.flashcard.dto.impl.ShortAnswerFlashcardDto;
import com.vincennlin.flashcardservice.payload.flashcard.dto.impl.TrueFalseFlashcardDto;

import java.util.List;

public interface FlashcardService {

    List<AbstractFlashcardDto> getFlashcardsByNoteId(Long noteId);

    AbstractFlashcardDto getFlashcardById(Long flashcardId);

    AbstractFlashcardDto createFlashcard(Long noteId, AbstractFlashcardDto flashcardDto);

    AbstractFlashcardDto updateFlashcard(Long flashcardId, AbstractFlashcardDto flashcardDto);

    void deleteFlashcardById(Long flashcardId);
}

package com.vincennlin.flashcardwebbackend.service;

import com.vincennlin.flashcardwebbackend.payload.flashcard.FlashcardDto;
import com.vincennlin.flashcardwebbackend.payload.flashcard.concrete.FillInTheBlankFlashcardDto;
import com.vincennlin.flashcardwebbackend.payload.flashcard.concrete.MultipleChoiceFlashcardDto;
import com.vincennlin.flashcardwebbackend.payload.flashcard.concrete.ShortAnswerFlashcardDto;
import com.vincennlin.flashcardwebbackend.payload.flashcard.concrete.TrueFalseFlashcardDto;

import java.util.List;

public interface FlashcardService {

    List<FlashcardDto> getFlashcardsByNoteId(Long noteId);

    FlashcardDto getFlashcardById(Long flashcardId);

    FlashcardDto createFlashcard(Long noteId, ShortAnswerFlashcardDto shortAnswerFlashcardDto);

    FlashcardDto createFlashcard(Long noteId, FillInTheBlankFlashcardDto fillInTheBlankFlashcardDto);

    FlashcardDto createFlashcard(Long noteId, MultipleChoiceFlashcardDto multipleChoiceFlashcardDto);

    FlashcardDto createFlashcard(Long noteId, TrueFalseFlashcardDto trueFalseFlashcardDto);

    FlashcardDto updateFlashcard(Long flashcardId, ShortAnswerFlashcardDto shortAnswerFlashcardDto);

    FlashcardDto updateFlashcard(Long flashcardId, FillInTheBlankFlashcardDto fillInTheBlankFlashcardDto);

    FlashcardDto updateFlashcard(Long flashcardId, MultipleChoiceFlashcardDto multipleChoiceFlashcardDto);

    FlashcardDto updateFlashcard(Long flashcardId, TrueFalseFlashcardDto trueFalseFlashcardDto);

    void deleteFlashcardById(Long flashcardId);
}

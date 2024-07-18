package com.vincennlin.flashcardservice.service;

import com.vincennlin.flashcardservice.payload.AbstractFlashcardDto;
import com.vincennlin.flashcardservice.payload.concrete.FillInTheBlankFlashcardDto;
import com.vincennlin.flashcardservice.payload.concrete.MultipleChoiceFlashcardDto;
import com.vincennlin.flashcardservice.payload.concrete.ShortAnswerFlashcardDto;
import com.vincennlin.flashcardservice.payload.concrete.TrueFalseFlashcardDto;

import java.util.List;

public interface FlashcardService {

    List<AbstractFlashcardDto> getFlashcardsByNoteId(Long noteId);

    AbstractFlashcardDto getFlashcardById(Long flashcardId);

    AbstractFlashcardDto createFlashcard(Long noteId, ShortAnswerFlashcardDto shortAnswerFlashcardDto);

    AbstractFlashcardDto createFlashcard(Long noteId, FillInTheBlankFlashcardDto fillInTheBlankFlashcardDto);

    AbstractFlashcardDto createFlashcard(Long noteId, MultipleChoiceFlashcardDto multipleChoiceFlashcardDto);

    AbstractFlashcardDto createFlashcard(Long noteId, TrueFalseFlashcardDto trueFalseFlashcardDto);

    AbstractFlashcardDto updateFlashcard(Long flashcardId, ShortAnswerFlashcardDto shortAnswerFlashcardDto);

    AbstractFlashcardDto updateFlashcard(Long flashcardId, FillInTheBlankFlashcardDto fillInTheBlankFlashcardDto);

    AbstractFlashcardDto updateFlashcard(Long flashcardId, MultipleChoiceFlashcardDto multipleChoiceFlashcardDto);

    AbstractFlashcardDto updateFlashcard(Long flashcardId, TrueFalseFlashcardDto trueFalseFlashcardDto);

    void deleteFlashcardById(Long flashcardId);
}

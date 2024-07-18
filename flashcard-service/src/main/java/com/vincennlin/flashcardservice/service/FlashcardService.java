package com.vincennlin.flashcardservice.service;

import com.vincennlin.flashcardservice.payload.FlashcardDto;
import com.vincennlin.flashcardservice.payload.concrete.FillInTheBlankFlashcardDto;
import com.vincennlin.flashcardservice.payload.concrete.MultipleChoiceFlashcardDto;
import com.vincennlin.flashcardservice.payload.concrete.ShortAnswerFlashcardDto;
import com.vincennlin.flashcardservice.payload.concrete.TrueFalseFlashcardDto;

import java.util.List;

public interface FlashcardService {

    List<FlashcardDto> getFlashcardsByNoteId(Long noteId);

    FlashcardDto getFlashcardById(Long flashcardId);

    FlashcardDto createFlashcard(Long noteId, ShortAnswerFlashcardDto shortAnswerFlashcardDto);

    FlashcardDto createFlashcard(Long noteId, FillInTheBlankFlashcardDto fillInTheBlankFlashcardDto);

    FlashcardDto createFlashcard(Long noteId, MultipleChoiceFlashcardDto multipleChoiceFlashcardDto);

    FlashcardDto createFlashcard(Long noteId, TrueFalseFlashcardDto trueFalseFlashcardDto);

//    FlashcardDto updateFlashcard(Long flashcardId, ShortAnswerFlashcardDto shortAnswerFlashcardDto);
//
//    FlashcardDto updateFlashcard(Long flashcardId, FillInTheBlankFlashcardDto fillInTheBlankFlashcardDto);
//
//    FlashcardDto updateFlashcard(Long flashcardId, MultipleChoiceFlashcardDto multipleChoiceFlashcardDto);
//
//    FlashcardDto updateFlashcard(Long flashcardId, TrueFalseFlashcardDto trueFalseFlashcardDto);
//
//    void deleteFlashcardById(Long flashcardId);
}

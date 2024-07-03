package com.vincennlin.flashcardwebbackend.operation;

import com.vincennlin.flashcardwebbackend.payload.flashcard.concrete.FillInTheBlankFlashcardDto;
import com.vincennlin.flashcardwebbackend.payload.flashcard.concrete.MultipleChoiceFlashcardDto;
import com.vincennlin.flashcardwebbackend.payload.flashcard.concrete.ShortAnswerFlashcardDto;
import com.vincennlin.flashcardwebbackend.payload.flashcard.concrete.TrueFalseFlashcardDto;

public interface Operation {
    void apply(FillInTheBlankFlashcardDto fillInTheBlankFlashcardDto);
    void apply(MultipleChoiceFlashcardDto multipleChoiceFlashcardDto);
    void apply(ShortAnswerFlashcardDto shortAnswerFlashcardDto);
    void apply(TrueFalseFlashcardDto trueFalseFlashcardDto);
}
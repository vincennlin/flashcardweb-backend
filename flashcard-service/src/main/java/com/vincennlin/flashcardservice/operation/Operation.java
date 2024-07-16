package com.vincennlin.flashcardservice.operation;

import com.vincennlin.flashcardservice.payload.concrete.FillInTheBlankFlashcardDto;
import com.vincennlin.flashcardservice.payload.concrete.MultipleChoiceFlashcardDto;
import com.vincennlin.flashcardservice.payload.concrete.ShortAnswerFlashcardDto;
import com.vincennlin.flashcardservice.payload.concrete.TrueFalseFlashcardDto;

public interface Operation {
    void apply(FillInTheBlankFlashcardDto fillInTheBlankFlashcardDto);
    void apply(MultipleChoiceFlashcardDto multipleChoiceFlashcardDto);
    void apply(ShortAnswerFlashcardDto shortAnswerFlashcardDto);
    void apply(TrueFalseFlashcardDto trueFalseFlashcardDto);
}
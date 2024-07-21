package com.vincennlin.flashcardservice.operation;

import com.vincennlin.flashcardservice.payload.flashcard.dto.impl.FillInTheBlankFlashcardDto;
import com.vincennlin.flashcardservice.payload.flashcard.dto.impl.MultipleChoiceFlashcardDto;
import com.vincennlin.flashcardservice.payload.flashcard.dto.impl.ShortAnswerFlashcardDto;
import com.vincennlin.flashcardservice.payload.flashcard.dto.impl.TrueFalseFlashcardDto;

public interface Operation {
    void apply(FillInTheBlankFlashcardDto fillInTheBlankFlashcardDto);
    void apply(MultipleChoiceFlashcardDto multipleChoiceFlashcardDto);
    void apply(ShortAnswerFlashcardDto shortAnswerFlashcardDto);
    void apply(TrueFalseFlashcardDto trueFalseFlashcardDto);
}
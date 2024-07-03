package com.vincennlin.flashcardwebbackend.operation.impl;

import com.vincennlin.flashcardwebbackend.entity.flashcard.Flashcard;
import com.vincennlin.flashcardwebbackend.entity.flashcard.concrete.FillInTheBlankFlashcard;
import com.vincennlin.flashcardwebbackend.entity.flashcard.concrete.MultipleChoiceFlashcard;
import com.vincennlin.flashcardwebbackend.entity.flashcard.concrete.ShortAnswerFlashcard;
import com.vincennlin.flashcardwebbackend.entity.flashcard.concrete.TrueFalseFlashcard;
import com.vincennlin.flashcardwebbackend.operation.Operation;
import com.vincennlin.flashcardwebbackend.payload.flashcard.concrete.FillInTheBlankFlashcardDto;
import com.vincennlin.flashcardwebbackend.payload.flashcard.concrete.MultipleChoiceFlashcardDto;
import com.vincennlin.flashcardwebbackend.payload.flashcard.concrete.ShortAnswerFlashcardDto;
import com.vincennlin.flashcardwebbackend.payload.flashcard.concrete.TrueFalseFlashcardDto;
import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Getter
@Component
public class MapToEntityOperation implements Operation {

    @Autowired
    private ModelMapper modelMapper;

    private Flashcard flashcard;

    @Override
    public void apply(FillInTheBlankFlashcardDto fillInTheBlankFlashcardDto) {
        flashcard = modelMapper.map(fillInTheBlankFlashcardDto, FillInTheBlankFlashcard.class);
    }

    @Override
    public void apply(MultipleChoiceFlashcardDto multipleChoiceFlashcardDto) {
        flashcard = modelMapper.map(multipleChoiceFlashcardDto, MultipleChoiceFlashcard.class);
    }

    @Override
    public void apply(ShortAnswerFlashcardDto shortAnswerFlashcardDto) {
        flashcard = modelMapper.map(shortAnswerFlashcardDto, ShortAnswerFlashcard.class);
    }

    @Override
    public void apply(TrueFalseFlashcardDto trueFalseFlashcardDto) {
        flashcard = modelMapper.map(trueFalseFlashcardDto, TrueFalseFlashcard.class);
    }
}

package com.vincennlin.flashcardservice.payload.flashcard.type;

import com.vincennlin.flashcardservice.entity.flashcard.AbstractFlashcard;
import com.vincennlin.flashcardservice.payload.flashcard.dto.AbstractFlashcardDto;

public interface AbstractFlashcardType {

    AbstractFlashcardDto getFlashcardExampleDto();

    Class<? extends AbstractFlashcardDto> getFlashcardDtoClass();

    Class<? extends AbstractFlashcard> getFlashcardEntityClass();
}

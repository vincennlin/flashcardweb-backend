package com.vincennlin.flashcardservice.payload.flashcard.type;

import com.vincennlin.flashcardservice.entity.flashcard.Flashcard;
import com.vincennlin.flashcardservice.payload.flashcard.dto.FlashcardDto;

public interface AbstractFlashcardType {

    FlashcardDto getFlashcardExampleDto();

    Class<? extends FlashcardDto> getFlashcardDtoClass();

    Class<? extends Flashcard> getFlashcardEntityClass();
}

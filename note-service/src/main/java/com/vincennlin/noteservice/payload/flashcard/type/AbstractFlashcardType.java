package com.vincennlin.noteservice.payload.flashcard.type;

import com.vincennlin.noteservice.payload.flashcard.dto.AbstractFlashcardDto;

public interface AbstractFlashcardType {

    AbstractFlashcardDto getFlashcardExampleDto();

    Class<? extends AbstractFlashcardDto> getFlashcardDtoClass();
}

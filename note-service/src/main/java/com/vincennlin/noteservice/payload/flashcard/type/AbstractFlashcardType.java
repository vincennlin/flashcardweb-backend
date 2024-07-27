package com.vincennlin.noteservice.payload.flashcard.type;

import com.vincennlin.noteservice.payload.flashcard.dto.FlashcardDto;

public interface AbstractFlashcardType {

    FlashcardDto getFlashcardExampleDto();

    Class<? extends FlashcardDto> getFlashcardDtoClass();
}

package com.vincennlin.courseservice.payload.flashcard.type;


import com.vincennlin.courseservice.payload.flashcard.FlashcardDto;

public interface AbstractFlashcardType {

    FlashcardDto getFlashcardExampleDto();

    Class<? extends FlashcardDto> getFlashcardDtoClass();
}

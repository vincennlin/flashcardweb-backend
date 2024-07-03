package com.vincennlin.flashcardwebbackend.payload.flashcard;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FlashcardList {

    private List<FlashcardDto> flashcardDtoList;

    public void add(FlashcardDto flashcardDto) {
        flashcardDtoList.add(flashcardDto);
    }
}

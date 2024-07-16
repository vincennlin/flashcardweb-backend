package com.vincennlin.flashcardservice.exception;

import com.vincennlin.flashcardservice.constant.FlashcardType;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class FlashcardTypeException extends RuntimeException{

    private Long flashcardId;
    private FlashcardType requiredType;
    private FlashcardType givenType;

    public FlashcardTypeException(Long flashcardId, FlashcardType requiredType, FlashcardType givenType) {

        super(String.format("Flashcard with id: '%s' is of type '%s' but '%s' is given",
                flashcardId, requiredType, givenType));
        this.flashcardId = flashcardId;
        this.requiredType = requiredType;
        this.givenType = givenType;
    }
}

package com.vincennlin.flashcardwebbackend.exception;

import com.vincennlin.flashcardwebbackend.constant.FlashcardType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class FlashcardTypeException extends RuntimeException{

    private Long flashcardId;
    private FlashcardType requiredType;
    private FlashcardType givenType;

    public FlashcardTypeException(Long flashcardId, FlashcardType requiredType, FlashcardType givenType) {

        // Flashcard type mismatch: Flashcard with id: '1' is of type 'MultipleChoice' but 'TrueFalse' is given
        super(String.format("Flashcard type mismatch: Flashcard with id: '%s' is of type '%s' but '%s' is given",
                flashcardId, requiredType, givenType));
        this.flashcardId = flashcardId;
        this.requiredType = requiredType;
        this.givenType = givenType;
    }

    public Long getFlashcardId() {
        return flashcardId;
    }

    public FlashcardType getRequiredType() {
        return requiredType;
    }

    public FlashcardType getGivenType() {
        return givenType;
    }
}

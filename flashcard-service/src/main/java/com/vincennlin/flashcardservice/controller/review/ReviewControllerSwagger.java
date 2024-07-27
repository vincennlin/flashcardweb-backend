package com.vincennlin.flashcardservice.controller.review;

import com.vincennlin.flashcardservice.payload.flashcard.dto.AbstractFlashcardDto;
import com.vincennlin.flashcardservice.payload.review.option.AbstractReviewOption;

public interface ReviewControllerSwagger {

    AbstractFlashcardDto reviewFlashcard(Long flashcardId, AbstractReviewOption abstractReviewOption);
}

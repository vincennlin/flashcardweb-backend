package com.vincennlin.flashcardservice.controller.review;

import com.vincennlin.flashcardservice.payload.flashcard.dto.FlashcardDto;
import com.vincennlin.flashcardservice.payload.review.option.AbstractReviewOption;

public interface ReviewControllerSwagger {

    FlashcardDto reviewFlashcard(Long flashcardId, AbstractReviewOption abstractReviewOption);
}

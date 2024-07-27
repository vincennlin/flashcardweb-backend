package com.vincennlin.flashcardservice.service;

import com.vincennlin.flashcardservice.payload.flashcard.dto.FlashcardDto;
import com.vincennlin.flashcardservice.payload.review.request.ReviewRequest;

import java.util.List;

public interface ReviewService {

    List<FlashcardDto> getFlashcardsToReview();

    FlashcardDto reviewFlashcard(Long flashcardId, ReviewRequest request);

    FlashcardDto undoReviewFlashcard(Long flashcardId);
}

package com.vincennlin.flashcardservice.service;

import com.vincennlin.flashcardservice.payload.flashcard.dto.FlashcardDto;
import com.vincennlin.flashcardservice.payload.review.dto.ReviewStateDto;
import com.vincennlin.flashcardservice.payload.review.request.ReviewRequest;

import java.util.List;

public interface ReviewService {

    List<FlashcardDto> getFlashcardsToReview();

    List<ReviewStateDto> getReviewStatesByFlashcardId(Long flashcardId);

    FlashcardDto reviewFlashcard(Long flashcardId, ReviewRequest request);

    FlashcardDto undoReviewFlashcard(Long flashcardId);
}

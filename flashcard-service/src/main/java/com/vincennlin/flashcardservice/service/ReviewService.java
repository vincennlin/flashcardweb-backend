package com.vincennlin.flashcardservice.service;

import com.vincennlin.flashcardservice.payload.flashcard.dto.FlashcardDto;
import com.vincennlin.flashcardservice.payload.review.request.ReviewRequest;

public interface ReviewService {

    FlashcardDto reviewFlashcard(Long flashcardId, ReviewRequest request);
}

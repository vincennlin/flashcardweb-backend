package com.vincennlin.flashcardservice.service;

import com.vincennlin.flashcardservice.payload.flashcard.dto.AbstractFlashcardDto;
import com.vincennlin.flashcardservice.payload.review.request.ReviewRequest;

public interface ReviewService {

    AbstractFlashcardDto reviewFlashcard(Long flashcardId, ReviewRequest request);
}

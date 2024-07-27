package com.vincennlin.flashcardservice.service.impl;

import com.vincennlin.flashcardservice.entity.flashcard.Flashcard;
import com.vincennlin.flashcardservice.mapper.FlashcardMapper;
import com.vincennlin.flashcardservice.payload.flashcard.dto.FlashcardDto;
import com.vincennlin.flashcardservice.payload.review.request.ReviewRequest;
import com.vincennlin.flashcardservice.repository.FlashcardRepository;
import com.vincennlin.flashcardservice.service.FlashcardService;
import com.vincennlin.flashcardservice.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {

    private final FlashcardService flashcardService;

    private final FlashcardRepository flashcardRepository;

    private final FlashcardMapper flashcardMapper;

    public FlashcardDto reviewFlashcard(Long flashcardId, ReviewRequest request) {

        Flashcard flashcard = flashcardService.getFlashcardEntityById(flashcardId);

        flashcard.review(request.getReviewOption());

        Flashcard updatedFlashcard = flashcardRepository.save(flashcard);

        return flashcardMapper.mapToDto(updatedFlashcard);
    }
}

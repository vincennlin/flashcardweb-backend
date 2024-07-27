package com.vincennlin.flashcardservice.service.impl;

import com.vincennlin.flashcardservice.entity.flashcard.Flashcard;
import com.vincennlin.flashcardservice.entity.review.ReviewInfo;
import com.vincennlin.flashcardservice.entity.review.ReviewState;
import com.vincennlin.flashcardservice.exception.WebAPIException;
import com.vincennlin.flashcardservice.mapper.FlashcardMapper;
import com.vincennlin.flashcardservice.payload.flashcard.dto.FlashcardDto;
import com.vincennlin.flashcardservice.payload.review.dto.ReviewStateDto;
import com.vincennlin.flashcardservice.payload.review.request.ReviewRequest;
import com.vincennlin.flashcardservice.repository.FlashcardRepository;
import com.vincennlin.flashcardservice.repository.ReviewInfoRepository;
import com.vincennlin.flashcardservice.service.FlashcardService;
import com.vincennlin.flashcardservice.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ReviewServiceImpl implements ReviewService {

    private final FlashcardMapper flashcardMapper;

    private final FlashcardService flashcardService;

    private final FlashcardRepository flashcardRepository;
    private final ReviewInfoRepository reviewInfoRepository;

    @Override
    public List<FlashcardDto> getFlashcardsToReview() {

        Long userId = flashcardService.getCurrentUserId();

        List<Flashcard> dueFlashcards = reviewInfoRepository.findDueFlashcardsByUserId(userId);

        return dueFlashcards.stream().map(flashcardMapper::mapToDto).toList();
    }

    @Override
    public List<ReviewStateDto> getReviewStatesByFlashcardId(Long flashcardId) {

        Flashcard flashcard = flashcardService.getFlashcardEntityById(flashcardId);

        List<ReviewState> reviewStates = flashcard.getReviewInfo().getReviewStates();

        if (reviewStates.isEmpty()) {
            throw new WebAPIException(HttpStatus.NOT_FOUND, "No review states found for flashcard with id " + flashcardId);
        }

        return reviewStates.stream().map(flashcardMapper::mapReviewStateToDto).toList();
    }

    @Override
    public FlashcardDto reviewFlashcard(Long flashcardId, ReviewRequest request) {

        Flashcard flashcard = flashcardService.getFlashcardEntityById(flashcardId);

        flashcard.review(request.getReviewOption());

        Flashcard updatedFlashcard = flashcardRepository.save(flashcard);

        return flashcardMapper.mapToDto(updatedFlashcard);
    }

    @Override
    public FlashcardDto undoReviewFlashcard(Long flashcardId) {

        Flashcard flashcard = flashcardService.getFlashcardEntityById(flashcardId);

        ReviewInfo reviewInfo = flashcard.getReviewInfo();

        if (reviewInfo.getReviewStates().isEmpty()) {
            throw new WebAPIException(HttpStatus.BAD_REQUEST, "Cannot undo review for flashcard with id " + flashcardId + " because there is no review state to undo");
        }

        ReviewState lastReviewState = reviewInfo.popReviewState();

        reviewInfo.restoreState(lastReviewState);

        Flashcard updatedFlashcard = flashcardRepository.save(flashcard);

        return flashcardMapper.mapToDto(updatedFlashcard);
    }
}

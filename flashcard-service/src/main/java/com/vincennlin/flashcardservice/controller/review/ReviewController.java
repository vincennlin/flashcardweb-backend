package com.vincennlin.flashcardservice.controller.review;

import com.vincennlin.flashcardservice.payload.flashcard.dto.FlashcardDto;
import com.vincennlin.flashcardservice.payload.review.dto.ReviewStateDto;
import com.vincennlin.flashcardservice.payload.review.request.ReviewRequest;
import com.vincennlin.flashcardservice.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@Validated
@RequestMapping("/api/v1")
public class ReviewController implements ReviewControllerSwagger{

    private Environment env;

    private ReviewService reviewService;

    @GetMapping("/flashcards/review")
    public ResponseEntity<List<FlashcardDto>> getFlashcardsToReview() {
        List<FlashcardDto> flashcardsResponse = reviewService.getFlashcardsToReview();

        return new ResponseEntity<>(flashcardsResponse, HttpStatus.OK);
    }

    @GetMapping("/flashcard/{flashcard_id}/review-history")
    public ResponseEntity<List<ReviewStateDto>> getReviewHistoryByFlashcardId(@PathVariable(name = "flashcard_id") Long flashcardId) {
        List<ReviewStateDto> reviewHistory = reviewService.getReviewStatesByFlashcardId(flashcardId);

        return new ResponseEntity<>(reviewHistory, HttpStatus.OK);
    }

    @PostMapping("/flashcard/{flashcard_id}/review")
    public ResponseEntity<FlashcardDto> reviewFlashcard(@PathVariable(name = "flashcard_id") Long flashcardId,
                                                        @RequestBody ReviewRequest request) {

        FlashcardDto updatedFlashcard = reviewService.reviewFlashcard(flashcardId, request);

        return new ResponseEntity<>(updatedFlashcard, HttpStatus.OK);
    }

    @PutMapping("/flashcard/{flashcard_id}/undo-review")
    public ResponseEntity<FlashcardDto> undoReviewFlashcard(@PathVariable(name = "flashcard_id") Long flashcardId) {

        FlashcardDto updatedFlashcard = reviewService.undoReviewFlashcard(flashcardId);

        return new ResponseEntity<>(updatedFlashcard, HttpStatus.OK);
    }
}

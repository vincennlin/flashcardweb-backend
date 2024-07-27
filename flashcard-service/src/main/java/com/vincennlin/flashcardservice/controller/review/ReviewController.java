package com.vincennlin.flashcardservice.controller.review;

import com.vincennlin.flashcardservice.payload.flashcard.dto.AbstractFlashcardDto;
import com.vincennlin.flashcardservice.payload.review.request.ReviewRequest;
import com.vincennlin.flashcardservice.service.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@Validated
@RequestMapping("/api/v1")
public class ReviewController {

    private Environment env;

    private ReviewService reviewService;

    @PostMapping("flashcard/{flashcard_id}/review")
    public ResponseEntity<AbstractFlashcardDto> reviewFlashcard(@PathVariable(name = "flashcard_id") Long flashcardId,
                                                                @RequestBody ReviewRequest request) {

        AbstractFlashcardDto updatedFlashcard = reviewService.reviewFlashcard(flashcardId, request);

        return new ResponseEntity<>(updatedFlashcard, HttpStatus.OK);
    }
}

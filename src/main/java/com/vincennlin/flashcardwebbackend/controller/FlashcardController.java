package com.vincennlin.flashcardwebbackend.controller;

import com.vincennlin.flashcardwebbackend.payload.FlashcardDto;
import com.vincennlin.flashcardwebbackend.service.FlashcardService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/api/v1/notes/{noteId}/flashcards")
public class FlashcardController {

    private FlashcardService flashcardService;

    public FlashcardController(FlashcardService flashcardService) {
        this.flashcardService = flashcardService;
    }

    @PostMapping
    public ResponseEntity<FlashcardDto> createFlashcard(@PathVariable Long noteId,
                                                        @Valid @RequestBody FlashcardDto flashcardDto) {

        FlashcardDto flashcardResponse = flashcardService.createFlashcard(noteId, flashcardDto);

        return new ResponseEntity<>(flashcardResponse, HttpStatus.CREATED);
    }

}

package com.vincennlin.flashcardwebbackend.controller;

import com.vincennlin.flashcardwebbackend.payload.FlashcardDto;
import com.vincennlin.flashcardwebbackend.service.FlashcardService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1")
public class FlashcardController {

    private FlashcardService flashcardService;

    public FlashcardController(FlashcardService flashcardService) {
        this.flashcardService = flashcardService;
    }

    @GetMapping("/notes/{noteId}/flashcards")
    public ResponseEntity<List<FlashcardDto>> getFlashcardsByNoteId(@PathVariable Long noteId) {

        List<FlashcardDto> flashcardsResponse = flashcardService.getFlashcardsByNoteId(noteId);

        return new ResponseEntity<>(flashcardsResponse, HttpStatus.OK);
    }

    @GetMapping("/flashcards/{flashcardId}")
    public ResponseEntity<FlashcardDto> getFlashcardById(@PathVariable Long flashcardId) {

        FlashcardDto flashcardResponse = flashcardService.getFlashcardById(flashcardId);

        return new ResponseEntity<>(flashcardResponse, HttpStatus.OK);
    }

    @PostMapping("/notes/{noteId}/flashcards")
    public ResponseEntity<FlashcardDto> createFlashcard(@PathVariable Long noteId,
                                                        @Valid @RequestBody FlashcardDto flashcardDto) {

        FlashcardDto flashcardResponse = flashcardService.createFlashcard(noteId, flashcardDto);

        return new ResponseEntity<>(flashcardResponse, HttpStatus.CREATED);
    }

    @PutMapping("/flashcards/{flashcardId}")
    public ResponseEntity<FlashcardDto> updateFlashcard(@PathVariable Long flashcardId,
                                                        @Valid @RequestBody FlashcardDto flashcardDto) {

        FlashcardDto flashcardResponse = flashcardService.updateFlashcard(flashcardId, flashcardDto);

        return new ResponseEntity<>(flashcardResponse, HttpStatus.OK);
    }
}

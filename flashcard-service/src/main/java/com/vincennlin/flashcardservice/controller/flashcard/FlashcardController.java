package com.vincennlin.flashcardservice.controller.flashcard;

import com.vincennlin.flashcardservice.payload.flashcard.dto.AbstractFlashcardDto;
import com.vincennlin.flashcardservice.payload.tag.TagDto;
import com.vincennlin.flashcardservice.service.FlashcardService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@Validated
@RequestMapping("/api/v1")
public class FlashcardController implements FlashcardControllerSwagger {

    private Environment env;

    private FlashcardService flashcardService;

    @GetMapping("/status/check")
    public ResponseEntity<String> status() {
        return new ResponseEntity<>("Flashcard Service is up and running on port " + env.getProperty("local.server.port"), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/notes/{note_id}/flashcards")
    public ResponseEntity<List<AbstractFlashcardDto>> getFlashcardsByNoteId(@PathVariable(name = "note_id") @Min(1) Long noteId) {

        List<AbstractFlashcardDto> flashcardsResponse = flashcardService.getFlashcardsByNoteId(noteId);

        return new ResponseEntity<>(flashcardsResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/flashcards/{flashcard_id}")
    public ResponseEntity<AbstractFlashcardDto> getFlashcardById(@PathVariable(name = "flashcard_id") @Min(1) Long flashcardId) {

        AbstractFlashcardDto flashcardResponse = flashcardService.getFlashcardById(flashcardId);

        return new ResponseEntity<>(flashcardResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/flashcards/tag")
    public ResponseEntity<List<AbstractFlashcardDto>> getFlashcardsByTag(@Valid @RequestBody TagDto tagDto) {

        List<AbstractFlashcardDto> flashcardsResponse = flashcardService.getFlashcardsByTag(tagDto);

        return new ResponseEntity<>(flashcardsResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('CREATE')")
    @PostMapping("/notes/{note_id}/flashcard")
    public ResponseEntity<AbstractFlashcardDto> createFlashcard(@PathVariable(name = "note_id") @Min(1) Long noteId,
                                                                           @Valid @RequestBody AbstractFlashcardDto flashcardDto){

        AbstractFlashcardDto flashcardResponse = flashcardService.createFlashcard(noteId, flashcardDto);

        return new ResponseEntity<>(flashcardResponse, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('CREATE')")
    @PostMapping("/notes/{note_id}/flashcards")
    public ResponseEntity<List<AbstractFlashcardDto>> createFlashcards(@PathVariable(name = "note_id") @Min(1) Long noteId,
                                                                       @RequestBody List<AbstractFlashcardDto> flashcardDtoList) {

        List<AbstractFlashcardDto> createdFlashcards = flashcardService.createFlashcards(noteId, flashcardDtoList);

        return new ResponseEntity<>(createdFlashcards, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('UPDATE')")
    @PutMapping("/flashcard/{flashcard_id}")
    public ResponseEntity<AbstractFlashcardDto> updateFlashcard(@PathVariable(name = "flashcard_id") @Min(1) Long flashcardId,
                                                                           @Valid @RequestBody AbstractFlashcardDto flashcardDto) {

        AbstractFlashcardDto flashcardResponse = flashcardService.updateFlashcard(flashcardId, flashcardDto);

        return new ResponseEntity<>(flashcardResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DELETE')")
    @DeleteMapping("/flashcard/{flashcard_id}")
    public ResponseEntity<Void> deleteFlashcardById(@PathVariable(name = "flashcard_id") @Min(1) Long flashcardId) {

        flashcardService.deleteFlashcardById(flashcardId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PreAuthorize("hasAuthority('DELETE')")
    @DeleteMapping("/notes/{note_id}/flashcards")
    public ResponseEntity<Void> deleteFlashcardsByNoteId(@PathVariable(name = "note_id") @Min(1) Long noteId) {

        flashcardService.deleteFlashcardsByNoteId(noteId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

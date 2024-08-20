package com.vincennlin.flashcardservice.controller.flashcard;

import com.vincennlin.flashcardservice.constant.AppConstants;
import com.vincennlin.flashcardservice.payload.deck.FlashcardCountInfo;
import com.vincennlin.flashcardservice.payload.flashcard.dto.FlashcardDto;
import com.vincennlin.flashcardservice.payload.flashcard.page.FlashcardPageResponse;
import com.vincennlin.flashcardservice.service.FlashcardService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    @GetMapping("/decks/{deck_id}/flashcards")
    public ResponseEntity<List<FlashcardDto>> getFlashcardsByDeckId(@PathVariable(name = "deck_id") @Min(1) Long deckId) {

        List<FlashcardDto> flashcardsResponse = flashcardService.getFlashcardsByDeckId(deckId);

        return new ResponseEntity<>(flashcardsResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/notes/{note_id}/flashcards")
    public ResponseEntity<List<FlashcardDto>> getFlashcardsByNoteId(@PathVariable(name = "note_id") @Min(1) Long noteId) {

        List<FlashcardDto> flashcardsResponse = flashcardService.getFlashcardsByNoteId(noteId);

        return new ResponseEntity<>(flashcardsResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/flashcards/{flashcard_id}")
    public ResponseEntity<FlashcardDto> getFlashcardById(@PathVariable(name = "flashcard_id") @Min(1) Long flashcardId) {

        FlashcardDto flashcardResponse = flashcardService.getFlashcardById(flashcardId);

        return new ResponseEntity<>(flashcardResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/flashcards/tags")
    public ResponseEntity<List<FlashcardDto>> getFlashcardsByTagNames(@RequestParam(name = "tag") List<String> tagNames) {

        List<FlashcardDto> flashcardsResponse = flashcardService.getFlashcardsByTagNames(tagNames);

        return new ResponseEntity<>(flashcardsResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/notes/flashcards/count")
    public ResponseEntity<FlashcardCountInfo> getFlashcardCountInfo() {

        FlashcardCountInfo flashcardCountInfo = flashcardService.getFlashcardCountInfo();

        return new ResponseEntity<>(flashcardCountInfo, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/flashcards/search")
    public ResponseEntity<FlashcardPageResponse> findFlashcardsByKeyword(
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) @Min(0) Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) @Max(100) @Min(1) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir) {

        Pageable pageable = PageRequest.of(pageNo, pageSize,
                sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

        FlashcardPageResponse flashcardsResponse = flashcardService.findFlashcardsByKeyword(keyword, pageable);

        return new ResponseEntity<>(flashcardsResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/decks/{deck_id}/flashcards/search")
    public ResponseEntity<FlashcardPageResponse> findFlashcardsByDeckIdAndKeyword(
            @PathVariable(name = "deck_id") @Min(1) Long deckId,
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) @Min(0) Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) @Max(100) @Min(1) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir) {

        Pageable pageable = PageRequest.of(pageNo, pageSize,
                sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

        FlashcardPageResponse flashcardsResponse = flashcardService.findFlashcardsByDeckIdAndKeyword(deckId, keyword, pageable);

        return new ResponseEntity<>(flashcardsResponse, HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('CREATE')")
    @PostMapping("/notes/{note_id}/flashcards")
    public ResponseEntity<FlashcardDto> createFlashcard(@PathVariable(name = "note_id") @Min(1) Long noteId,
                                                        @Valid @RequestBody FlashcardDto flashcardDto){

        FlashcardDto flashcardResponse = flashcardService.createFlashcard(noteId, flashcardDto);

        return new ResponseEntity<>(flashcardResponse, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('CREATE')")
    @PostMapping("/notes/{note_id}/flashcards/bulk")
    public ResponseEntity<List<FlashcardDto>> createFlashcards(@PathVariable(name = "note_id") @Min(1) Long noteId,
                                                               @RequestBody List<FlashcardDto> flashcardDtoList) {

        List<FlashcardDto> createdFlashcards = flashcardService.createFlashcards(noteId, flashcardDtoList);

        return new ResponseEntity<>(createdFlashcards, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('UPDATE')")
    @PutMapping("/flashcards/{flashcard_id}")
    public ResponseEntity<FlashcardDto> updateFlashcard(@PathVariable(name = "flashcard_id") @Min(1) Long flashcardId,
                                                        @Valid @RequestBody FlashcardDto flashcardDto) {

        FlashcardDto flashcardResponse = flashcardService.updateFlashcard(flashcardId, flashcardDto);

        return new ResponseEntity<>(flashcardResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DELETE')")
    @DeleteMapping("/flashcards/{flashcard_id}")
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

package com.vincennlin.noteservice.controller.note;

import com.vincennlin.noteservice.constant.AppConstants;
import com.vincennlin.noteservice.payload.flashcard.dto.FlashcardDto;
import com.vincennlin.noteservice.payload.request.GenerateFlashcardRequest;
import com.vincennlin.noteservice.payload.note.dto.NoteDto;
import com.vincennlin.noteservice.payload.note.page.NotePageResponse;
import com.vincennlin.noteservice.payload.request.GenerateFlashcardsRequest;
import com.vincennlin.noteservice.service.NoteService;
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
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@Validated
@RequestMapping("/api/v1")
public class NoteController implements NoteControllerSwagger {

    private Environment env;

    private NoteService noteService;

    @GetMapping("/notes/status/check")
    public ResponseEntity<String> status() {
        return new ResponseEntity<>("Note Service is up and running on port " + env.getProperty("local.server.port"), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/notes")
    public ResponseEntity<NotePageResponse> getAllNotes(
            @RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) @Min(0) Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) @Max(100) @Min(1) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir) {

        Pageable pageable = PageRequest.of(pageNo, pageSize,
                sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

        NotePageResponse notePageResponse = noteService.getAllNotes(pageable);

        return new ResponseEntity<>(notePageResponse, HttpStatus.OK);
    }

    @PostAuthorize("principal == #userId or hasAuthority('ADVANCED')")
    @GetMapping("/notes/user/{user_id}")
    public ResponseEntity<NotePageResponse> getNotesByUserId(
            @PathVariable(name = "user_id") @Min(1) Long userId,
            @RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) @Min(0) Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) @Max(100) @Min(1) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir) {

        Pageable pageable = PageRequest.of(pageNo, pageSize,
                sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

        NotePageResponse notePageResponse = noteService.getNotesByUserId(userId, pageable);

        return new ResponseEntity<>(notePageResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/deck/{deck_id}/notes")
    public ResponseEntity<NotePageResponse> getNotesByDeckId(
            @PathVariable(name = "deck_id") @Min(1) Long deckId,
            @RequestParam(name = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) @Min(0) Integer pageNo,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) @Max(100) @Min(1) Integer pageSize,
            @RequestParam(name = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(name = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIR, required = false) String sortDir) {

        Pageable pageable = PageRequest.of(pageNo, pageSize,
                sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

        NotePageResponse notePageResponse = noteService.getNotesByDeckId(deckId, pageable);

        return new ResponseEntity<>(notePageResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ')")
    @PostAuthorize("returnObject.body.userId == principal or hasAuthority('ADVANCED')")
    @GetMapping("/notes/{note_id}")
    public ResponseEntity<NoteDto> getNoteById(@PathVariable(name = "note_id") @Min(1) Long id) {

        NoteDto noteResponse = noteService.getNoteById(id);

        return new ResponseEntity<>(noteResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('CREATE')")
    @PostMapping("/notes")
    public ResponseEntity<NoteDto> createNote(@Valid @RequestBody NoteDto noteDto) {

        NoteDto noteResponse = noteService.createNote(noteDto);

        return new ResponseEntity<>(noteResponse, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('UPDATE')")
    @PutMapping("/notes/{note_id}")
    public ResponseEntity<NoteDto> updateNote(@PathVariable(name = "note_id") @Min(1) Long id,
                                              @RequestHeader("Authorization") String authorization,
                                              @Valid @RequestBody NoteDto noteDto) {

        NoteDto noteResponse = noteService.updateNote(id, noteDto);

        return new ResponseEntity<>(noteResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DELETE')")
    @DeleteMapping("/notes/{note_id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable(name = "note_id") @Min(1) Long id) {

        noteService.deleteNoteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/notes/{note_id}/is-owner")
    public ResponseEntity<Boolean> isNoteOwner(@PathVariable(name = "note_id") @Min(1) Long id) {
        return new ResponseEntity<>(noteService.isNoteOwner(id), HttpStatus.OK);
    }

    @PostMapping("/notes/{note_id}/generate/flashcard")
    public ResponseEntity<FlashcardDto> generateFlashcard(@PathVariable(name = "note_id") @Min(1) Long id,
                                                          @RequestBody GenerateFlashcardRequest request) {

        FlashcardDto flashcard = noteService.generateFlashcard(id, request);

        return new ResponseEntity<>(flashcard, HttpStatus.OK);
    }

    @PostMapping("/notes/{note_id}/generate/flashcards")
    public ResponseEntity<List<FlashcardDto>> generateFlashcards(@PathVariable(name = "note_id") @Min(1) Long id,
                                                                 @RequestBody GenerateFlashcardsRequest request) {

        List<FlashcardDto> flashcards = noteService.generateFlashcards(id, request);

        return new ResponseEntity<>(flashcards, HttpStatus.OK);
    }
}

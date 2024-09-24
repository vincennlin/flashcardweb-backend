package com.vincennlin.noteservice.client;

import com.vincennlin.noteservice.payload.deck.response.FlashcardCountInfo;
import com.vincennlin.noteservice.payload.flashcard.dto.FlashcardDto;
import com.vincennlin.noteservice.payload.flashcard.page.FlashcardPageResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "flashcard-ws")
public interface FlashcardServiceClient {

    @GetMapping("/api/v1/notes/{note_id}/flashcards")
    ResponseEntity<FlashcardPageResponse> getFlashcardsByNoteId(@PathVariable("note_id") Long noteId,
                                                                @RequestHeader("Authorization") String authorization);

    @GetMapping("/api/v1/notes/flashcards/count")
    ResponseEntity<FlashcardCountInfo> getFlashcardCountInfo(@RequestHeader("Authorization") String authorization);

    @DeleteMapping("api/v1/notes/{note_id}/flashcards")
    ResponseEntity<Void> deleteFlashcardsByNoteId(@PathVariable("note_id") Long noteId,
                                                  @RequestHeader("Authorization") String authorization);

    @DeleteMapping("api/v1/decks/{deck_id}/flashcards")
    ResponseEntity<Void> deleteFlashcardsByDeckId(@PathVariable("deck_id") Long deckId,
                                                  @RequestHeader("Authorization") String authorization);

    @PostMapping("/api/v1/notes/{note_id}/flashcards")
    ResponseEntity<FlashcardDto> createFlashcard(@PathVariable("note_id") Long noteId,
                                                 @RequestBody FlashcardDto flashcardDto,
                                                 @RequestHeader("Authorization") String authorization);

    @PostMapping("/api/v1/notes/{note_id}/flashcards/bulk")
    ResponseEntity<List<FlashcardDto>> createFlashcards(@PathVariable("note_id") Long noteId,
                                                        @RequestBody List<FlashcardDto> flashcardDtoList,
                                                        @RequestHeader("Authorization") String authorization);
}

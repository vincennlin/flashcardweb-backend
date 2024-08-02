package com.vincennlin.noteservice.client;

import com.vincennlin.noteservice.payload.flashcard.dto.FlashcardDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "flashcard-ws")
public interface FlashcardServiceClient {

    @GetMapping("/api/v1/notes/{note_id}/flashcards")
    ResponseEntity<List<FlashcardDto>> getFlashcardsByNoteId(@PathVariable("note_id") Long noteId,
                                                             @RequestHeader("Authorization") String authorization);

    @GetMapping("/api/v1/notes/flashcards/count")
    ResponseEntity<Map<Long, Integer>> getNotesFlashcardsCountByUserId(@RequestHeader("Authorization") String authorization);

    @DeleteMapping("api/v1/notes/{note_id}/flashcards")
    ResponseEntity<Void> deleteFlashcardsByNoteId(@PathVariable("note_id") Long noteId,
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

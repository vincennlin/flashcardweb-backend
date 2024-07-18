package com.vincennlin.noteservice.client;

import com.vincennlin.noteservice.payload.flashcard.FlashcardDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "flashcard-ws")
public interface FlashcardServiceClient {

    @GetMapping("/api/v1/notes/{note_id}/flashcards")
    ResponseEntity<List<FlashcardDto>> getFlashcardsByNoteId(@PathVariable("note_id") Long noteId);
}

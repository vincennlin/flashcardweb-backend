package com.vincennlin.noteservice.client;

import com.vincennlin.noteservice.payload.NoteDto;
import com.vincennlin.noteservice.payload.flashcard.FlashcardDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ai-ws")
public interface AiServiceClient {

    @PostMapping("/api/v1/ai/generate/flashcard/short-answer")
    public ResponseEntity<FlashcardDto> generateShortAnswerFlashcard(@RequestBody NoteDto noteDto);
}

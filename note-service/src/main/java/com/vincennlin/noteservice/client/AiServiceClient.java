package com.vincennlin.noteservice.client;

import com.vincennlin.noteservice.payload.note.NoteDto;
import com.vincennlin.noteservice.payload.flashcard.dto.AbstractFlashcardDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "ai-ws")
public interface AiServiceClient {

    @PostMapping("/api/v1/ai/generate/flashcard/short-answer")
    public ResponseEntity<AbstractFlashcardDto> generateShortAnswerFlashcard(@RequestBody NoteDto noteDto);
}

package com.vincennlin.noteservice.client;

import com.vincennlin.noteservice.payload.note.NoteDto;
import com.vincennlin.noteservice.payload.flashcard.dto.AbstractFlashcardDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "ai-ws")
public interface AiServiceClient {

    @PostMapping("/api/v1/ai/generate/flashcard/short-answer")
    ResponseEntity<AbstractFlashcardDto> generateShortAnswerFlashcard(@RequestBody NoteDto noteDto,
                                                                      @RequestHeader("Authorization") String authorization);

    @PostMapping("/api/v1/ai/generate/flashcard/fill-in-the-blank")
    ResponseEntity<AbstractFlashcardDto> generateFillInTheBlankFlashcard(@RequestBody NoteDto noteDto,
                                                                         @RequestHeader("Authorization") String authorization);

    @PostMapping("/api/v1/ai/generate/flashcard/multiple-choice")
    ResponseEntity<AbstractFlashcardDto> generateMultipleChoiceFlashcard(@RequestBody NoteDto noteDto,
                                                                         @RequestHeader("Authorization") String authorization);

    @PostMapping("/api/v1/ai/generate/flashcard/true-false")
    ResponseEntity<AbstractFlashcardDto> generateTrueFalseFlashcard(@RequestBody NoteDto noteDto,
                                                                    @RequestHeader("Authorization") String authorization);
}

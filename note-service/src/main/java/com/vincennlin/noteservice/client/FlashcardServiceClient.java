package com.vincennlin.noteservice.client;

import com.vincennlin.noteservice.payload.flashcard.dto.AbstractFlashcardDto;
import com.vincennlin.noteservice.payload.flashcard.dto.impl.FillInTheBlankFlashcardDto;
import com.vincennlin.noteservice.payload.flashcard.dto.impl.MultipleChoiceFlashcardDto;
import com.vincennlin.noteservice.payload.flashcard.dto.impl.ShortAnswerFlashcardDto;
import com.vincennlin.noteservice.payload.flashcard.dto.impl.TrueFalseFlashcardDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "flashcard-ws")
public interface FlashcardServiceClient {

    @GetMapping("/api/v1/notes/{note_id}/flashcards")
    ResponseEntity<List<AbstractFlashcardDto>> getFlashcardsByNoteId(@PathVariable("note_id") Long noteId,
                                                                     @RequestHeader("Authorization") String authorization);

    @DeleteMapping("api/v1/notes/{note_id}/flashcards")
    ResponseEntity<Void> deleteFlashcardsByNoteId(@PathVariable("note_id") Long noteId,
                                                  @RequestHeader("Authorization") String authorization);

    @PostMapping("/api/v1/notes/{note_id}/flashcards/short-answer")
    ResponseEntity<AbstractFlashcardDto> generateShortAnswerFlashcard(@PathVariable("note_id") Long noteId,
                                                                      @RequestBody ShortAnswerFlashcardDto shortAnswerFlashcardDto,
                                                                      @RequestHeader("Authorization") String authorization);

    @PostMapping("/api/v1/notes/{note_id}/flashcards/fill-in-the-blank")
    ResponseEntity<AbstractFlashcardDto> generateFillInTheBlankFlashcard(@PathVariable("note_id") Long noteId,
                                                                         @RequestBody FillInTheBlankFlashcardDto fillInTheBlankFlashcardDto,
                                                                         @RequestHeader("Authorization") String authorization);

    @PostMapping("/api/v1/notes/{note_id}/flashcards/multiple-choice")
    ResponseEntity<AbstractFlashcardDto> generateMultipleChoiceFlashcard(@PathVariable("note_id") Long noteId,
                                                                         @RequestBody MultipleChoiceFlashcardDto multipleChoiceFlashcardDto,
                                                                         @RequestHeader("Authorization") String authorization);

    @PostMapping("/api/v1/notes/{note_id}/flashcards/true-false")
    ResponseEntity<AbstractFlashcardDto> generateTrueFalseFlashcard(@PathVariable("note_id") Long noteId,
                                                                    @RequestBody TrueFalseFlashcardDto trueFalseFlashcardDto,
                                                                    @RequestHeader("Authorization") String authorization);

    @PostMapping("/api/v1/notes/{note_id}/flashcards")
    ResponseEntity<List<AbstractFlashcardDto>> createFlashcards(@PathVariable("note_id") Long noteId,
                                                                @RequestBody List<AbstractFlashcardDto> flashcardDtoList,
                                                                @RequestHeader("Authorization") String authorization);
}

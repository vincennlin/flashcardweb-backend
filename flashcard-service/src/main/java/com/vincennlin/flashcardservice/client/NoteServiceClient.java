package com.vincennlin.flashcardservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "note-ws")
public interface NoteServiceClient {

    @GetMapping("api/v1/notes/{note_id}/is-owner")
    ResponseEntity<Boolean> isNoteOwner(@PathVariable("note_id") Long noteId,
                                        @RequestHeader String Authorization);

    @GetMapping("api/v1/decks/{deck_id}/is-owner")
    ResponseEntity<Boolean> isDeckOwner(@PathVariable("deck_id") Long deckId,
                                        @RequestHeader String Authorization);

    @GetMapping("api/v1/decks/{deck_id}/notes/ids")
    ResponseEntity<List<Long>> getNoteIdsByDeckId(@PathVariable("deck_id") Long deckId,
                                                  @RequestHeader String Authorization);
}

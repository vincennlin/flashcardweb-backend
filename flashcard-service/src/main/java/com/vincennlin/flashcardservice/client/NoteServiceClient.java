package com.vincennlin.flashcardservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "note-ws")
public interface NoteServiceClient {

    @GetMapping("api/v1/notes/{note_id}/is-owner")
    ResponseEntity<Boolean> isNoteOwner(@PathVariable("note_id") Long noteId,
                                        @RequestHeader String Authorization);
}

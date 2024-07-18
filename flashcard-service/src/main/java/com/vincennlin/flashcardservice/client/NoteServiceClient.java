package com.vincennlin.flashcardservice.client;

import com.vincennlin.flashcardservice.payload.NoteClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "note-ws")
public interface NoteServiceClient {

    @GetMapping("api/v1/notes/{note_id}")
    ResponseEntity<NoteClientResponse> getNoteById(@PathVariable("note_id") Long noteId);
}

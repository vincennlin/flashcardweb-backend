package com.vincennlin.courseservice.client;

import com.vincennlin.courseservice.payload.note.CreateNoteRequest;
import com.vincennlin.courseservice.payload.note.NoteClientResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "note-ws")
public interface NoteServiceClient {

    @PostMapping("/api/v1/decks/{deck_id}/notes")
    ResponseEntity<NoteClientResponse> createNote(@PathVariable("deck_id") Long deckId,
                                                  @RequestBody CreateNoteRequest request,
                                                  @RequestHeader String Authorization);
}

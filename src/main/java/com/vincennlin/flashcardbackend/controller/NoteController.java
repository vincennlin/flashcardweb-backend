package com.vincennlin.flashcardbackend.controller;

import com.vincennlin.flashcardbackend.payload.NoteDto;
import com.vincennlin.flashcardbackend.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/notes")
public class NoteController {

    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping
    public ResponseEntity<NoteDto> createNote(@RequestBody NoteDto noteDto) {

        NoteDto noteResponse = noteService.createNote(noteDto);

        return new ResponseEntity<>(noteResponse, HttpStatus.CREATED);
    }
}

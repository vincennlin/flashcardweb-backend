package com.vincennlin.flashcardwebbackend.controller;

import com.vincennlin.flashcardwebbackend.payload.NoteDto;
import com.vincennlin.flashcardwebbackend.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1/notes")
public class NoteController {

    private NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public ResponseEntity<List<NoteDto>> getAllNotes() {

        List<NoteDto> notes = noteService.getAllNotes();

        return new ResponseEntity<>(notes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NoteDto> getNoteById(@PathVariable Long id) {

        NoteDto note = noteService.getNoteById(id);

        return new ResponseEntity<>(note, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<NoteDto> createNote(@RequestBody NoteDto noteDto) {

        NoteDto noteResponse = noteService.createNote(noteDto);

        return new ResponseEntity<>(noteResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NoteDto> updateNote(@PathVariable Long id,
                                              @RequestBody NoteDto noteDto) {

        NoteDto noteResponse = noteService.updateNote(id, noteDto);

        return new ResponseEntity<>(noteResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoteById(@PathVariable Long id) {

        noteService.deleteNoteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

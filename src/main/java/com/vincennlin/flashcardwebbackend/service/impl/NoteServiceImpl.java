package com.vincennlin.flashcardwebbackend.service.impl;

import com.vincennlin.flashcardwebbackend.entity.Note;
import com.vincennlin.flashcardwebbackend.exception.ResourceNotFoundException;
import com.vincennlin.flashcardwebbackend.payload.NoteDto;
import com.vincennlin.flashcardwebbackend.repository.NoteRepository;
import com.vincennlin.flashcardwebbackend.service.NoteService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteServiceImpl implements NoteService {

    private NoteRepository noteRepository;

    private ModelMapper modelMapper;

    public NoteServiceImpl(NoteRepository noteRepository, ModelMapper modelMapper) {
        this.noteRepository = noteRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<NoteDto> getAllNotes() {

        List<Note> notes = noteRepository.findAll();

        return notes.stream().map(note -> modelMapper.map(note, NoteDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public NoteDto getNoteById(Long noteId) {

        Note note = noteRepository.findById(noteId).orElseThrow(() ->
                new ResourceNotFoundException("Note", "id", noteId));

        return modelMapper.map(note, NoteDto.class);
    }

    @Override
    public NoteDto createNote(NoteDto noteDto) {

        Note note = modelMapper.map(noteDto, Note.class);

        Note newNote = noteRepository.save(note);

        return modelMapper.map(newNote, NoteDto.class);
    }

    @Override
    public NoteDto updateNote(Long noteId, NoteDto noteDto) {

        Note note = noteRepository.findById(noteId).orElseThrow(() ->
                new ResourceNotFoundException("Note", "id", noteId));

        note.setContent(noteDto.getContent());

        Note updatedNote = noteRepository.save(note);

        return modelMapper.map(updatedNote, NoteDto.class);
    }

    @Override
    public void deleteNoteById(Long noteId) {

        Note note = noteRepository.findById(noteId).orElseThrow(() ->
                new ResourceNotFoundException("Note", "id", noteId));

        noteRepository.delete(note);
    }
}

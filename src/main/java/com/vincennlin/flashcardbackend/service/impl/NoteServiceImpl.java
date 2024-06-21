package com.vincennlin.flashcardbackend.service.impl;

import com.vincennlin.flashcardbackend.entity.Note;
import com.vincennlin.flashcardbackend.exception.ResourceNotFoundException;
import com.vincennlin.flashcardbackend.payload.NoteDto;
import com.vincennlin.flashcardbackend.repository.NoteRepository;
import com.vincennlin.flashcardbackend.service.NoteService;
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
}

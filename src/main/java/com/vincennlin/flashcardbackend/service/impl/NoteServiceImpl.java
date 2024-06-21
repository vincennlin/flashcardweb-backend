package com.vincennlin.flashcardbackend.service.impl;

import com.vincennlin.flashcardbackend.entity.Note;
import com.vincennlin.flashcardbackend.payload.NoteDto;
import com.vincennlin.flashcardbackend.repository.NoteRepository;
import com.vincennlin.flashcardbackend.service.NoteService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class NoteServiceImpl implements NoteService {

    private NoteRepository noteRepository;

    private ModelMapper modelMapper;

    public NoteServiceImpl(NoteRepository noteRepository, ModelMapper modelMapper) {
        this.noteRepository = noteRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public NoteDto createNote(NoteDto noteDto) {

        Note note = modelMapper.map(noteDto, Note.class);

        Note newNote = noteRepository.save(note);

        return modelMapper.map(newNote, NoteDto.class);
    }
}

package com.vincennlin.flashcardwebbackend.service.impl;

import com.vincennlin.flashcardwebbackend.entity.Note;
import com.vincennlin.flashcardwebbackend.exception.ResourceNotFoundException;
import com.vincennlin.flashcardwebbackend.payload.note.NoteDto;
import com.vincennlin.flashcardwebbackend.payload.note.NotePageResponse;
import com.vincennlin.flashcardwebbackend.repository.NoteRepository;
import com.vincennlin.flashcardwebbackend.service.NoteService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {

    private NoteRepository noteRepository;

    private ModelMapper modelMapper;

    public NoteServiceImpl(NoteRepository noteRepository, ModelMapper modelMapper) {
        this.noteRepository = noteRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public NotePageResponse getAllNotes(Pageable pageable) {

        Page<Note> pageOfNotes = noteRepository.findAll(pageable);

        List<Note> listOfNotes = pageOfNotes.getContent();

        List<NoteDto> content = listOfNotes.stream().map(note ->
                modelMapper.map(note, NoteDto.class)).toList();

        NotePageResponse notePageResponse = new NotePageResponse();
        notePageResponse.setContent(content);
        notePageResponse.setPageNo(pageOfNotes.getNumber());
        notePageResponse.setPageSize(pageOfNotes.getSize());
        notePageResponse.setTotalElements(pageOfNotes.getTotalElements());
        notePageResponse.setTotalPages(pageOfNotes.getTotalPages());
        notePageResponse.setLast(pageOfNotes.isLast());

        return notePageResponse;
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

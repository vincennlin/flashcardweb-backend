package com.vincennlin.flashcardwebbackend.service.impl;

import com.vincennlin.flashcardwebbackend.entity.Note;
import com.vincennlin.flashcardwebbackend.exception.ResourceNotFoundException;
import com.vincennlin.flashcardwebbackend.exception.ResourceOwnershipException;
import com.vincennlin.flashcardwebbackend.payload.flashcard.FlashcardDto;
import com.vincennlin.flashcardwebbackend.payload.note.NoteDto;
import com.vincennlin.flashcardwebbackend.payload.note.NotePageResponse;
import com.vincennlin.flashcardwebbackend.repository.NoteRepository;
import com.vincennlin.flashcardwebbackend.security.FlashcardwebUserDetails;
import com.vincennlin.flashcardwebbackend.service.AuthService;
import com.vincennlin.flashcardwebbackend.service.FlashcardService;
import com.vincennlin.flashcardwebbackend.service.NoteService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class NoteServiceImpl implements NoteService {

    private NoteRepository noteRepository;

    private ModelMapper modelMapper;

    private AuthService authService;

    private FlashcardService flashcardService;

    @Override
    public NotePageResponse getAllNotes(Pageable pageable) {

        if (currentRoleContainsAdmin()) {
            return getNotePageResponse(noteRepository.findAll(pageable));
        }
        return getNotePageResponse(noteRepository.findByUserId(getCurrentUserId(), pageable));
    }

    @Override
    public NotePageResponse getNotesByUserId(Long userId, Pageable pageable) {

        Page<Note> pageOfNotes = noteRepository.findByUserId(userId, pageable);

        return getNotePageResponse(pageOfNotes);
    }

    @Override
    public NoteDto getNoteById(Long noteId) {

        Note note = noteRepository.findById(noteId).orElseThrow(() ->
                new ResourceNotFoundException("Note", "id", noteId));

        authorizeOwnership(note.getUser().getId());

        List<FlashcardDto> flashcardDtoList = flashcardService.getFlashcardsByNoteId(noteId);

        NoteDto noteDto = modelMapper.map(note, NoteDto.class);

        noteDto.setFlashcards(flashcardDtoList);

        return noteDto;
    }

    @Override
    public NoteDto createNote(NoteDto noteDto) {

        noteDto.setUserId(getCurrentUserId());

        Note note = modelMapper.map(noteDto, Note.class);

        Note newNote = noteRepository.save(note);

        return modelMapper.map(newNote, NoteDto.class);
    }

    @Override
    @Transactional
    public NoteDto updateNote(Long noteId, NoteDto noteDto) {

        Note note = noteRepository.findById(noteId).orElseThrow(() ->
                new ResourceNotFoundException("Note", "id", noteId));

        authorizeOwnership(note.getUser().getId());

        note.setContent(noteDto.getContent());

        Note updatedNote = noteRepository.save(note);

        NoteDto updatedNoteDto = modelMapper.map(updatedNote, NoteDto.class);

        updatedNoteDto.setFlashcards(flashcardService.getFlashcardsByNoteId(noteId));

        return updatedNoteDto;
    }

    @Override
    @Transactional
    public void deleteNoteById(Long noteId) {

        Note note = noteRepository.findById(noteId).orElseThrow(() ->
                new ResourceNotFoundException("Note", "id", noteId));

        authorizeOwnership(note.getUser().getId());

        noteRepository.delete(note);
    }

    private FlashcardwebUserDetails getUserDetails() {
        return (FlashcardwebUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private void authorizeOwnership(Long userId) {
        Long currentUserId = getCurrentUserId();
        if (!currentUserId.equals(userId) && !containsRole("ROLE_ADMIN")) {
            throw new ResourceOwnershipException(currentUserId, userId);
        }
    }

    private Long getCurrentUserId() {
        return getUserDetails().getUserId();
    }

    private Boolean currentRoleContainsAdmin() {
        return containsRole("ROLE_ADMIN");
    }

    private Boolean containsRole(String roleName) {
        return getUserDetails().getAuthorities().stream().anyMatch(
                authority -> authority.getAuthority().equals(roleName));
    }

    private NotePageResponse getNotePageResponse(Page<Note> pageOfNotes) {
        List<Note> listOfNotes = pageOfNotes.getContent();

        List<NoteDto> content = listOfNotes.stream().map(note ->
                modelMapper.map(note, NoteDto.class)).toList();

        for (NoteDto noteDto : content) {
            List<FlashcardDto> flashcardDtoList = flashcardService.getFlashcardsByNoteId(noteDto.getId());
            noteDto.setFlashcards(flashcardDtoList);
        }

        NotePageResponse notePageResponse = new NotePageResponse();
        notePageResponse.setContent(content);
        notePageResponse.setPageNo(pageOfNotes.getNumber());
        notePageResponse.setPageSize(pageOfNotes.getSize());
        notePageResponse.setTotalElements(pageOfNotes.getTotalElements());
        notePageResponse.setTotalPages(pageOfNotes.getTotalPages());
        notePageResponse.setLast(pageOfNotes.isLast());

        return notePageResponse;
    }
}

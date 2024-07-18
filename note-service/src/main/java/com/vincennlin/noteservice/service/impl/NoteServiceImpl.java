package com.vincennlin.noteservice.service.impl;

import com.vincennlin.noteservice.exception.ResourceOwnershipException;
import com.vincennlin.noteservice.exception.WebAPIException;
import com.vincennlin.noteservice.client.FlashcardServiceClient;
import com.vincennlin.noteservice.entity.Note;
import com.vincennlin.noteservice.exception.ResourceNotFoundException;
import com.vincennlin.noteservice.payload.NoteDto;
import com.vincennlin.noteservice.payload.NotePageResponse;
import com.vincennlin.noteservice.payload.flashcard.FlashcardDto;
import com.vincennlin.noteservice.repository.NoteRepository;
import com.vincennlin.noteservice.service.NoteService;
import feign.FeignException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class NoteServiceImpl implements NoteService {

    private final static Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);

    private NoteRepository noteRepository;

    private ModelMapper modelMapper;

    private FlashcardServiceClient flashcardServiceClient;

    @Override
    public NotePageResponse getAllNotes(Pageable pageable) {

//        if (currentRoleContainsAdmin()) {
//            return getNotePageResponse(noteRepository.findAll(pageable));
//        }
        return getNotePageResponse(noteRepository.findByUserId(getCurrentUserId(), pageable));
    }

    @Override
    public NotePageResponse getNotesByUserId(Long userId, Pageable pageable) {

        return getNotePageResponse(noteRepository.findByUserId(userId, pageable));
    }

    @Override
    public NoteDto getNoteById(Long noteId) {

        Note note = noteRepository.findById(noteId).orElseThrow(() ->
                new ResourceNotFoundException("Note", "id", noteId));

        List<FlashcardDto> flashcardDtoList = getFlashcardsByNoteId(noteId);

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

        authorizeOwnership(note.getUserId());

        note.setContent(noteDto.getContent());

        Note updatedNote = noteRepository.save(note);

        NoteDto updatedNoteDto = modelMapper.map(updatedNote, NoteDto.class);

        updatedNoteDto.setFlashcards(getFlashcardsByNoteId(noteId));

        return updatedNoteDto;
    }

    @Override
    @Transactional
    public void deleteNoteById(Long noteId) {

        Note note = noteRepository.findById(noteId).orElseThrow(() ->
                new ResourceNotFoundException("Note", "id", noteId));

        authorizeOwnership(note.getUserId());

        noteRepository.delete(note);
    }

    private Long getCurrentUserId() {
        return Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }

    private List<FlashcardDto> getFlashcardsByNoteId(Long noteId) {
        try{
            return flashcardServiceClient.getFlashcardsByNoteId(noteId).getBody();
        } catch (FeignException e) {
            logger.error(e.getLocalizedMessage());
            if (e.status() == HttpStatus.NOT_FOUND.value()){
                throw new ResourceNotFoundException("Flashcards", "note_id", noteId);
            }
            else {
                throw new WebAPIException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
            }
        }
    }

//    private FlashcardwebUserDetails getUserDetails() {
//        return (FlashcardwebUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//    }
//
    private void authorizeOwnership(Long ownerId) {
        Long currentUserId = getCurrentUserId();
//        if (!currentUserId.equals(userId) && !containsRole("ROLE_ADMIN")) {
//            throw new ResourceOwnershipException(currentUserId, userId);
//        }
        if (!currentUserId.equals(ownerId)) {
            throw new ResourceOwnershipException(currentUserId, ownerId);
        }
    }
//
//    private Boolean currentRoleContainsAdmin() {
//        return containsRole("ROLE_ADMIN");
//    }
//
//    private Boolean containsRole(String roleName) {
//        return getUserDetails().getAuthorities().stream().anyMatch(
//                authority -> authority.getAuthority().equals(roleName));
//    }

    private NotePageResponse getNotePageResponse(Page<Note> pageOfNotes) {
        List<Note> listOfNotes = pageOfNotes.getContent();

        List<NoteDto> content = listOfNotes.stream().map(note ->
                modelMapper.map(note, NoteDto.class)).toList();

        for (NoteDto noteDto : content) {
            List<FlashcardDto> flashcardDtoList = getFlashcardsByNoteId(noteDto.getId());
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

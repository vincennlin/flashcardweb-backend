package com.vincennlin.noteservice.service.impl;

import com.vincennlin.noteservice.client.AiServiceClient;
import com.vincennlin.noteservice.exception.ResourceOwnershipException;
import com.vincennlin.noteservice.exception.WebAPIException;
import com.vincennlin.noteservice.client.FlashcardServiceClient;
import com.vincennlin.noteservice.entity.Note;
import com.vincennlin.noteservice.exception.ResourceNotFoundException;
import com.vincennlin.noteservice.payload.flashcard.dto.impl.FillInTheBlankFlashcardDto;
import com.vincennlin.noteservice.payload.flashcard.dto.impl.MultipleChoiceFlashcardDto;
import com.vincennlin.noteservice.payload.flashcard.dto.impl.ShortAnswerFlashcardDto;
import com.vincennlin.noteservice.payload.flashcard.dto.impl.TrueFalseFlashcardDto;
import com.vincennlin.noteservice.payload.flashcard.type.FlashcardType;
import com.vincennlin.noteservice.payload.note.NoteDto;
import com.vincennlin.noteservice.payload.note.NotePageResponse;
import com.vincennlin.noteservice.payload.flashcard.dto.AbstractFlashcardDto;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class NoteServiceImpl implements NoteService {

    private final static Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);
    private final AiServiceClient aiServiceClient;

    private NoteRepository noteRepository;

    private ModelMapper modelMapper;

    private FlashcardServiceClient flashcardServiceClient;

    @Override
    public NotePageResponse getAllNotes(Pageable pageable) {

        if (containsAuthority("ADVANCED")) {
            return getNotePageResponse(noteRepository.findAll(pageable));
        }
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

        List<AbstractFlashcardDto> abstractFlashcardDtoList = getFlashcardsByNoteId(noteId);

        NoteDto noteDto = modelMapper.map(note, NoteDto.class);

        noteDto.setFlashcards(abstractFlashcardDtoList);

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

        flashcardServiceClient.deleteFlashcardsByNoteId(noteId, getAuthorization());
    }

    @Override
    public Boolean isNoteOwner(Long noteId) {

        Note note = noteRepository.findById(noteId).orElseThrow(() ->
                new ResourceNotFoundException("Note", "id", noteId));

        return note.getUserId().equals(getCurrentUserId());
    }

    @Override
    public AbstractFlashcardDto generateFlashcard(Long noteId, FlashcardType flashcardType) {

        Note note = noteRepository.findById(noteId).orElseThrow(() ->
                new ResourceNotFoundException("Note", "id", noteId));

        authorizeOwnership(note.getUserId());

        AbstractFlashcardDto generatedFlashcard = getGeneratedFlashcard(note, flashcardType);

        return saveGeneratedFlashcard(noteId, generatedFlashcard);
    }

    private Long getCurrentUserId() {
        return Long.parseLong(getAuthentication().getPrincipal().toString());
    }

    private String getAuthorization() {
        return getAuthentication().getCredentials().toString();
    }

    private List<AbstractFlashcardDto> getFlashcardsByNoteId(Long noteId) {
        try{
            return flashcardServiceClient.getFlashcardsByNoteId(noteId, getAuthorization()).getBody();
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

    private void authorizeOwnership(Long ownerId) {
        Long currentUserId = getCurrentUserId();
        if (!currentUserId.equals(ownerId) && !containsAuthority("ADVANCED")) {
            throw new ResourceOwnershipException(currentUserId, ownerId);
        }
    }

    private Boolean containsAuthority(String authorityName) {
        return getAuthentication().getAuthorities().stream().anyMatch(
                authority -> authority.getAuthority().equals(authorityName));
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private NotePageResponse getNotePageResponse(Page<Note> pageOfNotes) {
        List<Note> listOfNotes = pageOfNotes.getContent();

        List<NoteDto> content = listOfNotes.stream().map(note ->
                modelMapper.map(note, NoteDto.class)).toList();

        for (NoteDto noteDto : content) {
            List<AbstractFlashcardDto> abstractFlashcardDtoList = getFlashcardsByNoteId(noteDto.getId());
            noteDto.setFlashcards(abstractFlashcardDtoList);
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

    private AbstractFlashcardDto getGeneratedFlashcard(Note note, FlashcardType flashcardType) {
        switch (flashcardType) {
            case SHORT_ANSWER -> {
                return aiServiceClient.generateShortAnswerFlashcard(mapToDto(note), getAuthorization()).getBody();
            }
            case FILL_IN_THE_BLANK -> {
                return aiServiceClient.generateFillInTheBlankFlashcard(mapToDto(note), getAuthorization()).getBody();
            }
            case MULTIPLE_CHOICE -> {
                return aiServiceClient.generateMultipleChoiceFlashcard(mapToDto(note), getAuthorization()).getBody();
            }
            case TRUE_FALSE -> {
                return aiServiceClient.generateTrueFalseFlashcard(mapToDto(note), getAuthorization()).getBody();
            }
            default -> throw new WebAPIException(HttpStatus.BAD_REQUEST, "Invalid flashcard type");
        }
    }

    private AbstractFlashcardDto saveGeneratedFlashcard(Long noteId, AbstractFlashcardDto generatedFlashcard) {
        switch (generatedFlashcard.getType()) {
            case SHORT_ANSWER -> {
                return flashcardServiceClient.generateShortAnswerFlashcard(noteId,
                        (ShortAnswerFlashcardDto) generatedFlashcard, getAuthorization()).getBody();
            }
            case FILL_IN_THE_BLANK -> {
                return flashcardServiceClient.generateFillInTheBlankFlashcard(noteId,
                        (FillInTheBlankFlashcardDto) generatedFlashcard, getAuthorization()).getBody();
            }
            case MULTIPLE_CHOICE -> {
                return flashcardServiceClient.generateMultipleChoiceFlashcard(noteId,
                        (MultipleChoiceFlashcardDto) generatedFlashcard, getAuthorization()).getBody();
            }
            case TRUE_FALSE -> {
                return flashcardServiceClient.generateTrueFalseFlashcard(noteId,
                        (TrueFalseFlashcardDto) generatedFlashcard, getAuthorization()).getBody();
            }
            default -> throw new WebAPIException(HttpStatus.BAD_REQUEST, "Invalid flashcard type");
        }
    }

    private NoteDto mapToDto(Note note) {
        return modelMapper.map(note, NoteDto.class);
    }
}

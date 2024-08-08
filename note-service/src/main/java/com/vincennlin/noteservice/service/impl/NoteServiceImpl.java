package com.vincennlin.noteservice.service.impl;

import com.vincennlin.noteservice.client.AiServiceClient;
import com.vincennlin.noteservice.entity.deck.Deck;
import com.vincennlin.noteservice.exception.WebAPIException;
import com.vincennlin.noteservice.client.FlashcardServiceClient;
import com.vincennlin.noteservice.entity.note.Note;
import com.vincennlin.noteservice.exception.ResourceNotFoundException;
import com.vincennlin.noteservice.mapper.NoteMapper;
import com.vincennlin.noteservice.payload.deck.response.FlashcardCountInfo;
import com.vincennlin.noteservice.payload.extract.ExtractLanguage;
import com.vincennlin.noteservice.payload.note.dto.NoteDto;
import com.vincennlin.noteservice.payload.note.page.NotePageResponse;
import com.vincennlin.noteservice.payload.flashcard.dto.FlashcardDto;
import com.vincennlin.noteservice.payload.flashcard.request.GenerateFlashcardRequest;
import com.vincennlin.noteservice.payload.flashcard.request.GenerateFlashcardsRequest;
import com.vincennlin.noteservice.repository.DeckRepository;
import com.vincennlin.noteservice.repository.NoteRepository;
import com.vincennlin.noteservice.service.AuthService;
import com.vincennlin.noteservice.service.DeckService;
import com.vincennlin.noteservice.service.ExtractService;
import com.vincennlin.noteservice.service.NoteService;
import feign.FeignException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@AllArgsConstructor
@Service
public class NoteServiceImpl implements NoteService {

    private final static Logger logger = LoggerFactory.getLogger(NoteServiceImpl.class);

    private final NoteMapper noteMapper;

    private final DeckService deckService;
    private final ExtractService extractService;
    private final AuthService authService;

    private final NoteRepository noteRepository;
    private final DeckRepository deckRepository;

    private final FlashcardServiceClient flashcardServiceClient;
    private final AiServiceClient aiServiceClient;

    @Override
    public NotePageResponse getAllNotes(Pageable pageable) {

        if (authService.containsAuthority("ADVANCED")) {
            return getNotePageResponse(noteRepository.findAll(pageable));
        }
        return getNotePageResponse(noteRepository.findByUserId(authService.getCurrentUserId(), pageable));
    }

    @Override
    public NotePageResponse getNotesByUserId(Long userId, Pageable pageable) {

        return getNotePageResponse(noteRepository.findByUserId(userId, pageable));
    }

    @Override
    public NotePageResponse getNotesByDeckId(Long deckId, Pageable pageable) {

        Deck deck = deckRepository.findById(deckId).orElseThrow(() ->
                new ResourceNotFoundException("Deck", "id", deckId.toString()));

        authService.authorizeOwnership(deck.getUserId());

        List<Long> noteIds = deckService.getNoteIdsByDeck(deck);

        return getNotePageResponse(noteRepository.findByIdIn(noteIds, pageable));
    }

    @Override
    public NoteDto getNoteById(Long noteId) {

        Note note = noteRepository.findById(noteId).orElseThrow(() ->
                new ResourceNotFoundException("Note", "id", noteId.toString()));

        List<FlashcardDto> flashcardDtoList = getFlashcardsByNoteId(noteId);

        NoteDto noteDto = noteMapper.mapToDto(note, deckService.getFlashcardCountInfo());

        noteDto.setFlashcards(flashcardDtoList);

        return noteDto;
    }

    @Override
    public NoteDto createNote(Long deckId, NoteDto noteDto) {

        Deck deck = deckService.getDeckEntityById(deckId);

        noteDto.setUserId(authService.getCurrentUserId());

        Note note = noteMapper.mapToEntity(noteDto);
        note.setDeck(deck);

        Note newNote = noteRepository.save(note);

        return noteMapper.mapToDto(newNote);
    }

    @Override
    public NoteDto createNoteFromFile(Long deckId, MultipartFile file) {

        String pdfText = extractService.extractTextFromFile(file);

        NoteDto noteDto = new NoteDto();
        noteDto.setContent(pdfText);

        return createNote(deckId, noteDto);
    }

//    @Override
//    public NoteDto createNoteFromImage(Long deckId, ExtractLanguage language, MultipartFile imageFile) {
//
//        String imageText = extractService.extractTextFromImage(language, imageFile);
//
//        NoteDto noteDto = new NoteDto();
//        noteDto.setContent(imageText);
//
//        return createNote(deckId, noteDto);
//    }

    @Override
    @Transactional
    public NoteDto updateNote(Long noteId, NoteDto noteDto) {

        Note note = noteRepository.findById(noteId).orElseThrow(() ->
                new ResourceNotFoundException("Note", "id", noteId.toString()));

        authService.authorizeOwnership(note.getUserId());

        note.setContent(noteDto.getContent());

        Note updatedNote = noteRepository.save(note);

        NoteDto updatedNoteDto = noteMapper.mapToDto(updatedNote, deckService.getFlashcardCountInfo());

        updatedNoteDto.setFlashcards(getFlashcardsByNoteId(noteId));

        return updatedNoteDto;
    }

    @Override
    @Transactional
    public void deleteNoteById(Long noteId) {

        Note note = noteRepository.findById(noteId).orElseThrow(() ->
                new ResourceNotFoundException("Note", "id", noteId.toString()));

        authService.authorizeOwnership(note.getUserId());

        noteRepository.delete(note);

        flashcardServiceClient.deleteFlashcardsByNoteId(noteId, authService.getAuthorization());
    }

    @Override
    public Boolean isNoteOwner(Long noteId) {

        Note note = noteRepository.findById(noteId).orElseThrow(() ->
                new ResourceNotFoundException("Note", "id", noteId.toString()));

        return note.getUserId().equals(authService.getCurrentUserId());
    }

    @Override
    public FlashcardDto generateFlashcard(Long noteId, GenerateFlashcardRequest request) {

        Note note = noteRepository.findById(noteId).orElseThrow(() ->
                new ResourceNotFoundException("Note", "id", noteId.toString()));

        authService.authorizeOwnership(note.getUserId());

        request.setContent(note.getContent());

        FlashcardDto generatedFlashcard = aiServiceClient.generateFlashcard(request, authService.getAuthorization()).getBody();

        return flashcardServiceClient.createFlashcard(noteId, generatedFlashcard, authService.getAuthorization()).getBody();
    }

    @Override
    public List<FlashcardDto> generateFlashcards(Long noteId, GenerateFlashcardsRequest request) {

        Note note = noteRepository.findById(noteId).orElseThrow(() ->
                new ResourceNotFoundException("Note", "id", noteId.toString()));

        authService.authorizeOwnership(note.getUserId());

        request.setNote(noteMapper.mapToDto(note));

        List<FlashcardDto> generatedFlashcards = aiServiceClient.generateFlashcards(request, authService.getAuthorization()).getBody();

        return flashcardServiceClient.createFlashcards(noteId, generatedFlashcards, authService.getAuthorization()).getBody();
    }

    private List<FlashcardDto> getFlashcardsByNoteId(Long noteId) {
        try{
            return flashcardServiceClient.getFlashcardsByNoteId(noteId, authService.getAuthorization()).getBody();
        } catch (FeignException e) {
            logger.error(e.getLocalizedMessage());
            if (e.status() == HttpStatus.NOT_FOUND.value()){
                throw new ResourceNotFoundException("Flashcards", "note_id", noteId.toString());
            }
            else {
                throw new WebAPIException(HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
            }
        }
    }

    private NotePageResponse getNotePageResponse(Page<Note> pageOfNotes) {
        List<Note> listOfNotes = pageOfNotes.getContent();

        FlashcardCountInfo flashcardCountInfo = deckService.getFlashcardCountInfo();

        List<NoteDto> NoteDtoList = listOfNotes.stream().map(note -> {
            authService.authorizeOwnership(note.getUserId());
            return noteMapper.mapToDto(note, flashcardCountInfo);
        }).toList();

        for (NoteDto noteDto : NoteDtoList) {
            List<FlashcardDto> flashcardDtoList = getFlashcardsByNoteId(noteDto.getId());
            noteDto.setFlashcards(flashcardDtoList);
        }

        NotePageResponse notePageResponse = new NotePageResponse();
        notePageResponse.setContent(NoteDtoList);
        notePageResponse.setPageNo(pageOfNotes.getNumber());
        notePageResponse.setPageSize(pageOfNotes.getSize());
        notePageResponse.setTotalElements(pageOfNotes.getTotalElements());
        notePageResponse.setTotalPages(pageOfNotes.getTotalPages());
        notePageResponse.setLast(pageOfNotes.isLast());

        return notePageResponse;
    }
}

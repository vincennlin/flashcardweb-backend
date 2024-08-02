package com.vincennlin.noteservice.service.impl;

import com.vincennlin.noteservice.client.FlashcardServiceClient;
import com.vincennlin.noteservice.entity.deck.Deck;
import com.vincennlin.noteservice.exception.ResourceNotFoundException;
import com.vincennlin.noteservice.exception.WebAPIException;
import com.vincennlin.noteservice.mapper.NoteMapper;
import com.vincennlin.noteservice.payload.deck.dto.DeckDto;
import com.vincennlin.noteservice.payload.deck.request.CreateDeckRequest;
import com.vincennlin.noteservice.payload.deck.response.NoteIdFlashcardCount;
import com.vincennlin.noteservice.payload.note.dto.NoteDto;
import com.vincennlin.noteservice.repository.DeckRepository;
import com.vincennlin.noteservice.service.AuthService;
import com.vincennlin.noteservice.service.DeckService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Service
public class DeckServiceImpl implements DeckService {

    private final AuthService authService;

    private final DeckRepository deckRepository;
    private final NoteMapper noteMapper;

    private final FlashcardServiceClient flashcardServiceClient;

    @Override
    public List<DeckDto> getAllDecks() {

        Long userId = authService.getCurrentUserId();

        List<Deck> decks = deckRepository.findByUserIdAndParent(userId, null);

        return decks.stream().map(deck ->
                noteMapper.mapDeckToDto(deck, getNoteIdFlashcardCountMap())).toList();
    }

    @Override
    public DeckDto getDeckById(Long deckId) {

        Deck deck = getDeckEntityById(deckId);

        return noteMapper.mapDeckToDto(deck, getNoteIdFlashcardCountMap());
    }

    @Override
    public Deck getDeckEntityById(Long deckId) {

        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new ResourceNotFoundException("Deck", "id", deckId.toString()));

        authService.authorizeOwnership(deck.getUserId());

        return deck;
    }

    @Override
    public DeckDto createDeck(CreateDeckRequest request) {

        Long userId = authService.getCurrentUserId();

        if (deckRepository.existsByNameAndUserId(request.getName(), userId)) {
            throw new WebAPIException(HttpStatus.BAD_REQUEST, "Deck with name " + request.getName() + " already exists");
        }

        Deck deck = new Deck();
        deck.setName(request.getName());
        deck.setUserId(userId);

        if (request.getParentId() != null) {
            setParentDeck(deck, request.getParentId());
        } else {
            deck.setParent(null);
        }

        Deck newDeck = deckRepository.save(deck);

        return noteMapper.mapDeckToDto(newDeck, getNoteIdFlashcardCountMap());
    }

    @Override
    public DeckDto updateDeck(Long deckId, DeckDto deckDto) {

        Long userId = authService.getCurrentUserId();

        if (deckRepository.existsByNameAndUserId(deckDto.getName(), userId)) {
            throw new WebAPIException(HttpStatus.BAD_REQUEST, "Deck with name " + deckDto.getName() + " already exists");
        }

        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new ResourceNotFoundException("Deck", "id", deckId.toString()));

        authService.authorizeOwnership(deck.getUserId());

        deck.setName(deckDto.getName());

        if (deckDto.getParentId() != null) {
            setParentDeck(deck, deckDto.getParentId());
        } else {
            deck.setParent(null);
        }

        Deck updatedDeck = deckRepository.save(deck);

        return noteMapper.mapDeckToDto(updatedDeck, getNoteIdFlashcardCountMap());
    }

    @Override
    public void deleteDeckById(Long deckId) {

        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new ResourceNotFoundException("Deck", "id", deckId.toString()));

        if (deck == null) {
            throw new ResourceNotFoundException("Deck", "id", deckId.toString());
        }

        authService.authorizeOwnership(deck.getUserId());

        // TODO: 刪除 flashcard-ws 中，note_Id 為空的字卡

        deckRepository.delete(deck);
    }

    private void setParentDeck(Deck deck, Long parentId) {
        Deck parentDeck = deckRepository.findById(parentId)
                .orElseThrow(() -> new ResourceNotFoundException("Deck", "id", parentId.toString()));

        authService.authorizeOwnership(parentDeck.getUserId());

        deck.setParent(parentDeck);
    }

    private Map<Long, Integer> getNoteIdFlashcardCountMap() {
        try {
            return flashcardServiceClient.getNotesFlashcardsCountByUserId(authService.getAuthorization()).getBody();
        } catch (Exception e) {
            throw new WebAPIException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to get flashcard count");
        }
    }
}

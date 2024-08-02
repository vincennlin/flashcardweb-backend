package com.vincennlin.noteservice.service.impl;

import com.vincennlin.noteservice.entity.deck.Deck;
import com.vincennlin.noteservice.exception.ResourceNotFoundException;
import com.vincennlin.noteservice.exception.ResourceOwnershipException;
import com.vincennlin.noteservice.exception.WebAPIException;
import com.vincennlin.noteservice.mapper.NoteMapper;
import com.vincennlin.noteservice.payload.deck.dto.DeckDto;
import com.vincennlin.noteservice.repository.DeckRepository;
import com.vincennlin.noteservice.service.AuthService;
import com.vincennlin.noteservice.service.DeckService;
import com.vincennlin.noteservice.service.NoteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class DeckServiceImpl implements DeckService {

    private final AuthService authService;

    private final DeckRepository deckRepository;
    private final NoteMapper noteMapper;

    @Override
    public List<DeckDto> getAllDecks() {

        Long userId = authService.getCurrentUserId();

        List<Deck> decks = deckRepository.findByUserId(userId);

        return decks.stream().map(noteMapper::mapDeckToDto).toList();
    }

    @Override
    public DeckDto getDeckById(Long deckId) {

        Deck deck = getDeckEntityById(deckId);

        return noteMapper.mapDeckToDto(deck);
    }

    @Override
    public Deck getDeckEntityById(Long deckId) {

        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new ResourceNotFoundException("Deck", "id", deckId.toString()));

        authService.authorizeOwnership(deck.getUserId());

        return deck;
    }

    @Override
    public DeckDto createDeck(DeckDto deckDto) {

        Long userId = authService.getCurrentUserId();

        if (deckRepository.existsByNameAndUserId(deckDto.getName(), userId)) {
            throw new WebAPIException(HttpStatus.BAD_REQUEST, "Deck with name " + deckDto.getName() + " already exists");
        }

        Deck deck = new Deck();
        deck.setName(deckDto.getName());
        deck.setUserId(userId);

        Deck newDeck = deckRepository.save(deck);

        return noteMapper.mapDeckToDto(newDeck);
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

        Deck updatedDeck = deckRepository.save(deck);

        return noteMapper.mapDeckToDto(updatedDeck);
    }

    @Override
    public void deleteDeckById(Long deckId) {

        Deck deck = deckRepository.findById(deckId)
                .orElseThrow(() -> new ResourceNotFoundException("Deck", "id", deckId.toString()));

        if (deck == null) {
            throw new ResourceNotFoundException("Deck", "id", deckId.toString());
        }

        authService.authorizeOwnership(deck.getUserId());

        deckRepository.delete(deck);
    }
}

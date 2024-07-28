package com.vincennlin.noteservice.service;

import com.vincennlin.noteservice.payload.deck.dto.DeckDto;

import java.util.List;

public interface DeckService {

    List<DeckDto> getAllDecks();

    DeckDto getDeckById(Long deckId);

    DeckDto createDeck(DeckDto deckDto);

    DeckDto updateDeck(Long deckId, DeckDto deckDto);

    void deleteDeckById(Long deckId);
}

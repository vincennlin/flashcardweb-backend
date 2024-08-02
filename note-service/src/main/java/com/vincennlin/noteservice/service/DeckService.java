package com.vincennlin.noteservice.service;

import com.vincennlin.noteservice.entity.deck.Deck;
import com.vincennlin.noteservice.payload.deck.dto.DeckDto;
import com.vincennlin.noteservice.payload.deck.request.CreateDeckRequest;
import com.vincennlin.noteservice.payload.deck.response.FlashcardCountInfo;

import java.util.List;

public interface DeckService {

    List<DeckDto> getAllDecks();

    DeckDto getDeckById(Long deckId);

    Deck getDeckEntityById(Long deckId);

    List<Long> getNoteIdsByDeckId(Long deckId);

    DeckDto createDeck(CreateDeckRequest request);

    DeckDto updateDeck(Long deckId, DeckDto deckDto);

    void deleteDeckById(Long deckId);

    Boolean isDeckOwner(Long deckId);

    List<Long> getNoteIdsByDeck(Deck deck);

    FlashcardCountInfo getFlashcardCountInfo();
}

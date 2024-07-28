package com.vincennlin.noteservice.controller.deck;

import com.vincennlin.noteservice.payload.deck.dto.DeckDto;
import com.vincennlin.noteservice.service.DeckService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@Validated
@RequestMapping("/api/v1")
public class DeckController implements DeckControllerSwagger{

    private final DeckService deckService;

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/decks")
    public ResponseEntity<List<DeckDto>> getAllDecks() {

        List<DeckDto> decks = deckService.getAllDecks();

        return new ResponseEntity<>(decks, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/deck/{deck_id}")
    public ResponseEntity<DeckDto> getDeckById(@PathVariable(name = "deck_id") Long deckId) {

        DeckDto deck = deckService.getDeckById(deckId);

        return new ResponseEntity<>(deck, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('CREATE')")
    @PostMapping("/deck")
    public ResponseEntity<DeckDto> createDeck(@Valid @RequestBody DeckDto deckDto) {

        DeckDto newDeck = deckService.createDeck(deckDto);

        return new ResponseEntity<>(newDeck, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('UPDATE')")
    @PutMapping("/deck/{deck_id}")
    public ResponseEntity<DeckDto> updateDeck(@PathVariable(name = "deck_id") Long deckId,
                                              @Valid @RequestBody DeckDto deckDto) {

        DeckDto updatedDeck = deckService.updateDeck(deckId, deckDto);

        return new ResponseEntity<>(updatedDeck, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('DELETE')")
    @DeleteMapping("/deck/{deck_id}")
    public ResponseEntity<Void> deleteDeckById(@PathVariable(name = "deck_id") Long deckId) {

        deckService.deleteDeckById(deckId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

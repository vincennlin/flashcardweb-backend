package com.vincennlin.noteservice.mapper;

import com.vincennlin.noteservice.client.FlashcardServiceClient;
import com.vincennlin.noteservice.entity.deck.Deck;
import com.vincennlin.noteservice.entity.note.Note;
import com.vincennlin.noteservice.payload.deck.dto.DeckDto;
import com.vincennlin.noteservice.payload.deck.response.FlashcardCountInfo;
import com.vincennlin.noteservice.payload.note.dto.NoteDto;
import com.vincennlin.noteservice.service.AuthService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NoteMapper {

    private final ModelMapper modelMapper;

    @Autowired
    private AuthService authService;

    public NoteMapper() {
        this.modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Converter<Deck, DeckDto> deckConverter = context ->
                context.getSource() == null ? null : modelMapper.map(context.getSource(), DeckDto.class);
        modelMapper.addConverter(deckConverter);
    }

    public NoteDto mapToDto(Note note) {
        return mapToDto(note, new FlashcardCountInfo());
    }

    public NoteDto mapToDto(Note note, FlashcardCountInfo flashcardCountInfo) {
        NoteDto noteDto = modelMapper.map(note, NoteDto.class);
        noteDto.setDeckId(note.getDeck().getId());
        noteDto.setFlashcardCountInfo(flashcardCountInfo);
        return noteDto;
    }

    public Note mapToEntity(NoteDto noteDto) {
        return modelMapper.map(noteDto, Note.class);
    }

    public DeckDto mapDeckToDto(Deck deck, FlashcardCountInfo flashcardCountInfo) {
        DeckDto deckDto = modelMapper.map(deck, DeckDto.class);
        deckDto.setFlashcardCountInfo(flashcardCountInfo);
        deckDto.updateFlashcardCount();
        if (deck.getParent() != null) {
            deckDto.setParentId(deck.getParent().getId());
        }
        deckDto.getSubDecks().forEach(subDeck -> {
            subDeck.setParentId(deck.getId());
        });

        return deckDto;
    }
}

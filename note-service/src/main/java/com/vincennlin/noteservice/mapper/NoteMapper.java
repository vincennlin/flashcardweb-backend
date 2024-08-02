package com.vincennlin.noteservice.mapper;

import com.vincennlin.noteservice.client.FlashcardServiceClient;
import com.vincennlin.noteservice.entity.deck.Deck;
import com.vincennlin.noteservice.entity.note.Note;
import com.vincennlin.noteservice.payload.deck.dto.DeckDto;
import com.vincennlin.noteservice.payload.note.dto.NoteDto;
import com.vincennlin.noteservice.service.AuthService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class NoteMapper {

    private final ModelMapper modelMapper;

    @Autowired
    private FlashcardServiceClient flashcardServiceClient;

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
        NoteDto noteDto = modelMapper.map(note, NoteDto.class);
        noteDto.setDeckId(note.getDeck().getId());
        return noteDto;
    }

    public Note mapToEntity(NoteDto noteDto) {
        return modelMapper.map(noteDto, Note.class);
    }

    public DeckDto mapDeckToDto(Deck deck, Map<Long, Integer> noteIdFlashcardCountMap) {
        DeckDto deckDto = modelMapper.map(deck, DeckDto.class);
        deckDto.setNoteFlashcardCount(noteIdFlashcardCountMap);
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

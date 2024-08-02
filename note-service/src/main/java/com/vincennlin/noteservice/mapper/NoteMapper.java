package com.vincennlin.noteservice.mapper;

import com.vincennlin.noteservice.entity.deck.Deck;
import com.vincennlin.noteservice.entity.note.Note;
import com.vincennlin.noteservice.payload.deck.dto.DeckDto;
import com.vincennlin.noteservice.payload.note.dto.NoteDto;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

@Component
public class NoteMapper {

    private final ModelMapper modelMapper;

    public NoteMapper() {
        this.modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Converter<Deck, DeckDto> deckConverter = context ->
                context.getSource() == null ? null : modelMapper.map(context.getSource(), DeckDto.class);
        modelMapper.addConverter(deckConverter);
    }

    public NoteDto mapToDto(Note note) {
        return modelMapper.map(note, NoteDto.class);
    }

    public Note mapToEntity(NoteDto noteDto) {
        return modelMapper.map(noteDto, Note.class);
    }

    public DeckDto mapDeckToDto(Deck deck) {
        return modelMapper.map(deck, DeckDto.class);
    }
}

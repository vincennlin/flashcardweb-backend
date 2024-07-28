package com.vincennlin.noteservice.payload.deck.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vincennlin.noteservice.payload.note.dto.NoteDto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeckDto {

    @JsonProperty(value = "deck_id")
    private Long id;

    @NotBlank(message = "deck_name cannot be empty")
    @JsonProperty(value = "deck_name")
    private String name;

    @JsonProperty(value = "user_id")
    private Long userId;

    @JsonProperty(value = "notes")
    private List<NoteDto> notes;
}

package com.vincennlin.noteservice.payload.deck.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @JsonProperty(value = "id")
    private Long id;

    @NotBlank(message = "name cannot be empty")
    @JsonProperty(value = "name")
    private String name;

    @JsonProperty(value = "user_id")
    private Long userId;

    @JsonProperty(value = "parent_id")
    private Long parentId;

    @JsonProperty(value = "sub_decks")
    private List<DeckDto> subDecks;

    @JsonBackReference
    @JsonProperty(value = "notes")
    private List<NoteDto> notes;


}

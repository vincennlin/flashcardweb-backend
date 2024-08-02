package com.vincennlin.noteservice.payload.deck.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteIdFlashcardCount {

    @JsonProperty(value = "note_id")
    private Long noteId;

    @JsonProperty(value = "flashcard_count")
    private Long flashcardCount;
}

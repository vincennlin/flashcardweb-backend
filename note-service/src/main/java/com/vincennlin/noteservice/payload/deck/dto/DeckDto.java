package com.vincennlin.noteservice.payload.deck.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vincennlin.noteservice.payload.note.dto.NoteDto;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @JsonProperty(value = "note_count")
    private Integer noteCount;

    @JsonProperty(value = "flashcard_count")
    private Integer flashcardCount;

    @JsonProperty(value = "sub_decks")
    private List<DeckDto> subDecks;

    @JsonBackReference
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty(value = "notes")
    private List<NoteDto> notes;

    public Integer getNoteCount() {
        int total = notes.size();
        for (DeckDto subDeck : subDecks) {
            total += subDeck.getNoteCount();
        }
        this.setNoteCount(total);
        return total;
    }

    public void setNoteFlashcardCount(Map<Long, Integer> noteIdFlashcardCountMap) {
        for (NoteDto note : notes) {
            Integer flashcardCount = noteIdFlashcardCountMap.getOrDefault(note.getId(), 0);
            note.setFlashcardCount(flashcardCount);
        }
        for (DeckDto subDeck : subDecks) {
            subDeck.setNoteFlashcardCount(noteIdFlashcardCountMap);
        }
    }

    public Integer updateFlashcardCount() {
        Integer total = 0;
        for (NoteDto note : notes) {
            total += note.getFlashcardCount();
        }
        for (DeckDto subDeck : subDecks) {
            total += subDeck.getFlashcardCount();
        }
        this.setFlashcardCount(total);
        return total;
    }

    public Integer getFlashcardCount() {
        return updateFlashcardCount();
    }
}

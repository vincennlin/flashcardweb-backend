package com.vincennlin.noteservice.payload.deck.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vincennlin.noteservice.payload.deck.response.FlashcardCountInfo;
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

    @JsonProperty(value = "note_count")
    private Integer noteCount;

    @JsonProperty(value = "total_flashcard_count")
    private Integer totalFlashcardCount;

    @JsonProperty(value = "review_flashcard_count")
    private Integer reviewFlashcardCount;

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

    @JsonIgnore
    public void setParentId(Long parentId) {
        this.parentId = parentId;
        for (DeckDto subDeck : subDecks) {
            subDeck.setParentId(this.id);
        }
    }

    @JsonIgnore
    public void setFlashcardCountInfo(FlashcardCountInfo flashcardCountInfo) {
        for (NoteDto note : notes) {
            note.setFlashcardCountInfo(flashcardCountInfo);
        }
        for (DeckDto subDeck : subDecks) {
            subDeck.setFlashcardCountInfo(flashcardCountInfo);
        }
    }

    public void updateFlashcardCount() {
        Integer total = 0;
        Integer review = 0;
        for (NoteDto note : notes) {
            total += note.getTotalFlashcardCount();
            review += note.getReviewFlashcardCount();
        }
        for (DeckDto subDeck : subDecks) {
            total += subDeck.getTotalFlashcardCount();
            review += subDeck.getReviewFlashcardCount();
        }
        this.setTotalFlashcardCount(total);
        this.setReviewFlashcardCount(review);
    }

    public Integer getTotalFlashcardCount() {
        updateFlashcardCount();
        return this.totalFlashcardCount;
    }

    public Integer getReviewFlashcardCount() {
        updateFlashcardCount();
        return this.reviewFlashcardCount;
    }
}

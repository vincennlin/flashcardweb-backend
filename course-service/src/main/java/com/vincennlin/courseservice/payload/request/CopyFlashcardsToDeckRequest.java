package com.vincennlin.courseservice.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CopyFlashcardsToDeckRequest {

    @NotNull
    @JsonProperty(value = "deck_id")
    private Long deckId;

    @NotEmpty
    @JsonProperty(value = "flashcard_ids")
    private List<Long> flashcardIds;
}

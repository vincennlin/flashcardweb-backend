package com.vincennlin.courseservice.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlashcardIdsRequest {

    @JsonProperty(value = "flashcard_ids")
    private List<Long> flashcardIds;
}

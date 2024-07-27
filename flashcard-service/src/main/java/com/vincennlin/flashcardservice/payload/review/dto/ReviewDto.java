package com.vincennlin.flashcardservice.payload.review.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewDto {

    public ReviewDto() {
        this.reviewLevel = 0;
        this.reviewInterval = 1;
    }

    @JsonProperty("review_level")
    private Integer reviewLevel;

    @JsonProperty("review_interval")
    private Integer reviewInterval;

    @JsonProperty("last_reviewed")
    private String lastReviewed;

    @JsonProperty("next_review")
    private String nextReview;
}

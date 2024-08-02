package com.vincennlin.flashcardservice.payload.review.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewInfoDto {

    @JsonProperty("review_level")
    private Integer reviewLevel;

    @JsonProperty("review_interval")
    private Integer reviewInterval;

    @JsonProperty("last_reviewed")
    private LocalDateTime lastReviewed;

    @JsonProperty("next_review")
    private LocalDateTime nextReview;
}

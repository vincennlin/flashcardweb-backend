package com.vincennlin.flashcardservice.payload.review.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class ReviewInfoDto {

    public ReviewInfoDto() {
        this.reviewLevel = 0;
        this.reviewInterval = 1;
        this.nextReview = new Date();
    }

    @JsonProperty("review_level")
    private Integer reviewLevel;

    @JsonProperty("review_interval")
    private Integer reviewInterval;

    @JsonProperty("last_reviewed")
    private Date lastReviewed;

    @JsonProperty("next_review")
    private Date nextReview;
}

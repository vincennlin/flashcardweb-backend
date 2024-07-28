package com.vincennlin.flashcardservice.payload.review.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vincennlin.flashcardservice.payload.review.option.ReviewOption;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {

    @JsonProperty("review_option")
    private ReviewOption reviewOption;
}

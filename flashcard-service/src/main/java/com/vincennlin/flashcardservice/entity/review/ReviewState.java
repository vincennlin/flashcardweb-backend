package com.vincennlin.flashcardservice.entity.review;

import com.vincennlin.flashcardservice.payload.review.option.ReviewOption;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "review_states")
public class ReviewState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "review_option")
    private ReviewOption reviewOption;

    @Column(name = "review_level")
    private Integer reviewLevel;

    @Column(name = "review_interval")
    private Integer reviewInterval;

    @Column(name = "last_reviewed")
    private LocalDateTime lastReviewed;

    @Column(name = "next_review")
    private LocalDateTime nextReview;
}
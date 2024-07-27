package com.vincennlin.flashcardservice.entity.review;

import com.vincennlin.flashcardservice.entity.flashcard.Flashcard;
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
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "review_level")
    private Integer reviewLevel;

    @Column(name = "review_interval")
    private Integer reviewInterval;

    @Column(name = "last_reviewed")
    private String lastReviewed;

    @Column(name = "next_review")
    private String nextReview;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    private Flashcard flashcard;

    public void review(ReviewOption option) {
        this.reviewInterval += option.getInterval(this.reviewLevel);
        this.reviewLevel += 1;

        LocalDateTime now = LocalDateTime.now();
        this.lastReviewed = now.toString();
        this.nextReview = now.plusDays(this.reviewInterval).toString();
    }
}

package com.vincennlin.flashcardservice.entity.review;

import com.vincennlin.flashcardservice.entity.flashcard.Flashcard;
import com.vincennlin.flashcardservice.payload.review.option.ReviewOption;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "review_infos")
public class ReviewInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "review_level")
    private Integer reviewLevel;

    @Column(name = "review_interval")
    private Integer reviewInterval;

    @Column(name = "last_reviewed")
    private LocalDateTime lastReviewed;

    @Column(name = "next_review")
    private LocalDateTime nextReview;

    @OneToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL
    )
    @JoinColumn(name = "flashcard_id")
    private Flashcard flashcard;

    @OneToMany(
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    @JoinColumn(name = "review_info_id")
    private List<ReviewState> reviewStates = new ArrayList<>();

    public void review(ReviewOption option) {
        pushReviewState(this.createState(option));

        this.reviewInterval += option.getInterval(this.reviewLevel);
        this.reviewLevel += 1;

        LocalDateTime now = LocalDateTime.now();
        this.lastReviewed = now;
        this.nextReview = now.plusDays(this.reviewInterval);
    }

    public ReviewState createState(ReviewOption option) {
        return new ReviewState(null, option, this.reviewLevel,
                this.reviewInterval, this.lastReviewed, this.nextReview);
    }

    public void restoreState(ReviewState state) {
        this.reviewLevel = state.getReviewLevel();
        this.reviewInterval = state.getReviewInterval();
        this.lastReviewed = state.getLastReviewed();
        this.nextReview = state.getNextReview();
    }

    public void pushReviewState(ReviewState reviewState) {
        reviewStates.add(reviewState);
    }

    public ReviewState popReviewState() {
        return reviewStates.isEmpty() ? null : reviewStates.remove(reviewStates.size() - 1);
    }
}

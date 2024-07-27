package com.vincennlin.flashcardservice.repository;

import com.vincennlin.flashcardservice.entity.flashcard.Flashcard;
import com.vincennlin.flashcardservice.entity.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT f FROM Flashcard f JOIN f.review r WHERE f.userId = :userId AND r.nextReview < CURRENT_TIMESTAMP")
    List<Flashcard> findDueFlashcardsByUserId(Long userId);
}
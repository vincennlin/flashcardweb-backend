package com.vincennlin.flashcardservice.repository;

import com.vincennlin.flashcardservice.entity.review.ReviewState;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewStateRepository extends JpaRepository<ReviewState, Long> {
}
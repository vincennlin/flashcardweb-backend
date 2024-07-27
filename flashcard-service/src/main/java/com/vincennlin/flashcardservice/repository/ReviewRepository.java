package com.vincennlin.flashcardservice.repository;

import com.vincennlin.flashcardservice.entity.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
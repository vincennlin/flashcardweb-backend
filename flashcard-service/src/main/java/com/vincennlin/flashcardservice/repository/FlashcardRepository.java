package com.vincennlin.flashcardservice.repository;

import com.vincennlin.flashcardservice.entity.Flashcard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {
}

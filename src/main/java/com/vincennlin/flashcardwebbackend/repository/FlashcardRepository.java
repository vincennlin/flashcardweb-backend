package com.vincennlin.flashcardwebbackend.repository;

import com.vincennlin.flashcardwebbackend.entity.flashcard.Flashcard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {
}

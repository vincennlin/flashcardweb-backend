package com.vincennlin.flashcardservice.repository;

import com.vincennlin.flashcardservice.entity.Flashcard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {

    Optional<List<Flashcard>> findByNoteId(Long noteId);
}

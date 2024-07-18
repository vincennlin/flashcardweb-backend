package com.vincennlin.flashcardservice.repository;

import com.vincennlin.flashcardservice.entity.AbstractFlashcard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FlashcardRepository extends JpaRepository<AbstractFlashcard, Long> {

    Optional<List<AbstractFlashcard>> findByNoteId(Long noteId);
}

package com.vincennlin.flashcardservice.repository;

import com.vincennlin.flashcardservice.entity.AbstractFlashcard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlashcardRepository extends JpaRepository<AbstractFlashcard, Long> {

    List<AbstractFlashcard> findByNoteId(Long noteId);

    void deleteByNoteId(Long noteId);
}

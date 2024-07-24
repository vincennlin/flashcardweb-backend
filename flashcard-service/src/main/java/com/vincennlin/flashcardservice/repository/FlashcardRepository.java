package com.vincennlin.flashcardservice.repository;

import com.vincennlin.flashcardservice.entity.flashcard.AbstractFlashcard;
import com.vincennlin.flashcardservice.entity.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FlashcardRepository extends JpaRepository<AbstractFlashcard, Long> {

    List<AbstractFlashcard> findByNoteId(Long noteId);

    List<AbstractFlashcard> findByTags(List<Tag> tags);

    void deleteByNoteId(Long noteId);
}

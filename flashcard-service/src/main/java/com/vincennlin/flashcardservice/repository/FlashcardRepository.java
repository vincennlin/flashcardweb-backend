package com.vincennlin.flashcardservice.repository;

import com.vincennlin.flashcardservice.entity.flashcard.Flashcard;
import com.vincennlin.flashcardservice.entity.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {

    List<Flashcard> findByNoteId(Long noteId);

    @Query("SELECT f FROM Flashcard f JOIN f.tags t WHERE t IN :tags")
    List<Flashcard> findByTags(@Param("tags") List<Tag> tags);

    void deleteByNoteId(Long noteId);
}

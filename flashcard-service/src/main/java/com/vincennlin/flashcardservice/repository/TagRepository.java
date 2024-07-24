package com.vincennlin.flashcardservice.repository;

import com.vincennlin.flashcardservice.entity.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findByUserId(Long userId);

    List<Tag> findByTagNameContainsAndUserId(String tagName, Long userId);

    List<Tag> findByFlashcardsIdAndUserId(Long flashcardId, Long userId);

    Optional<Tag> findByTagNameAndUserId(String tagName, Long userId);

    @Query("SELECT SIZE(t.flashcards) FROM Tag t WHERE t.id = :tagId AND t.userId = :userId")
    Integer countFlashcardsByTagId(@Param("tagId") Long tagId, @Param("userId") Long userId);

    Boolean existsByTagNameAndUserId(String tagName, Long userId);
}

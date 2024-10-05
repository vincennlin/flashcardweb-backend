package com.vincennlin.flashcardservice.repository;

import com.vincennlin.flashcardservice.entity.flashcard.Flashcard;
import com.vincennlin.flashcardservice.entity.tag.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {

    Page<Flashcard> findByNoteId(Long noteId, Pageable pageable);

    Page<Flashcard> findByNoteIdIn(List<Long> noteIds, Pageable pageable);

    @Query("SELECT f FROM Flashcard f JOIN f.tags t WHERE t IN :tags")
    Page<Flashcard> findByTags(@Param("tags") List<Tag> tags, Pageable pageable);

    @Query("SELECT f.noteId, COUNT(f) FROM Flashcard f WHERE f.userId = :userId GROUP BY f.noteId")
    List<Object[]> findNoteIdAndFlashcardCountByUserId(@Param("userId") Long userId);

    @Query("SELECT f.noteId, COUNT(f) " +
            "FROM Flashcard f " +
            "JOIN f.reviewInfo r " +
            "WHERE f.userId = :userId AND r.nextReview < CURRENT_TIMESTAMP " +
            "GROUP BY f.noteId")
    List<Object[]> findNoteIdAndFlashcardCountByUserIdAndNextReviewPast(@Param("userId") Long userId);

    @Query("SELECT f FROM Flashcard f " +
            "LEFT JOIN FillInTheBlankFlashcard fib ON TYPE(f) = FillInTheBlankFlashcard AND fib.id = f.id " +
            "LEFT JOIN InBlankAnswer iba ON iba.flashcard = fib " +
            "LEFT JOIN MultipleChoiceFlashcard mc ON TYPE(f) = MultipleChoiceFlashcard AND mc.id = f.id " +
            "LEFT JOIN Option opt ON opt.flashcard = mc " +
            "LEFT JOIN ShortAnswerFlashcard sa ON TYPE(f) = ShortAnswerFlashcard AND sa.id = f.id " +
            "WHERE f.userId = :userId " +
            "AND (LOWER(f.question) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(f.extraInfo) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(fib.fullAnswer) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(iba.text) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(opt.text) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(sa.shortAnswer) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(f.type) LIKE LOWER(CONCAT('%', :keyword, '%'))) ")
    Page<Flashcard> findByUserIdAndContentContaining(@Param("userId") Long userId, @Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT f FROM Flashcard f " +
            "LEFT JOIN FillInTheBlankFlashcard fib ON TYPE(f) = FillInTheBlankFlashcard AND fib.id = f.id " +
            "LEFT JOIN InBlankAnswer iba ON iba.flashcard = fib " +
            "LEFT JOIN MultipleChoiceFlashcard mc ON TYPE(f) = MultipleChoiceFlashcard AND mc.id = f.id " +
            "LEFT JOIN Option opt ON opt.flashcard = mc " +
            "LEFT JOIN ShortAnswerFlashcard sa ON TYPE(f) = ShortAnswerFlashcard AND sa.id = f.id " +
            "WHERE f.noteId IN :noteIds " +
            "AND (LOWER(f.question) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(f.extraInfo) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(fib.fullAnswer) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(iba.text) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(opt.text) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(sa.shortAnswer) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(f.type) LIKE LOWER(CONCAT('%', :keyword, '%'))) ")
    Page<Flashcard> findByNoteIdInAndContentContaining(@Param("noteIds") List<Long> noteIds, @Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT f.id FROM Flashcard f WHERE f.userId = :userId AND f.id IN :ids")
    List<Long> getIdsByUserIdAndIdIn(@Param("userId") Long userId, @Param("ids") List<Long> ids);

    void deleteByNoteId(Long noteId);

    void deleteByNoteIdIn(List<Long> noteIds);
}

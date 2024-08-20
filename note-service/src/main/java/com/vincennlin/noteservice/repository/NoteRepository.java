package com.vincennlin.noteservice.repository;

import com.vincennlin.noteservice.entity.deck.Deck;
import com.vincennlin.noteservice.entity.note.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {

    Page<Note> findByUserId(Long userId, Pageable pageable);

    Page<Note> findByDeckId(Long deckId, Pageable pageable);

    Page<Note> findByIdIn(List<Long> noteIds, Pageable pageable);

    @Query("SELECT n FROM Note n WHERE n.userId = :userId AND n.content LIKE %:content%")
    Page<Note> findByUserIdAndContentContaining(@Param("userId") Long userId, @Param("content") String content, Pageable pageable);

    @Query("SELECT n FROM Note n WHERE n.deck = :deck AND n.content LIKE %:content%")
    Page<Note> findByDeckIdAndContentContaining(@Param("deck") Deck deck, @Param("content") String content, Pageable pageable);
}

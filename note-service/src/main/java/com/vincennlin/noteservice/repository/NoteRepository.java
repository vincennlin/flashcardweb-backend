package com.vincennlin.noteservice.repository;

import com.vincennlin.noteservice.entity.note.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {

    Page<Note> findByUserId(Long userId, Pageable pageable);

    Page<Note> findByDeckId(Long deckId, Pageable pageable);

    Page<Note> findByIdIn(List<Long> noteIds, Pageable pageable);
}

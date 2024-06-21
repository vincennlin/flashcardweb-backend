package com.vincennlin.flashcardbackend.repository;

import com.vincennlin.flashcardbackend.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
}

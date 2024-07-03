package com.vincennlin.flashcardwebbackend.repository;

import com.vincennlin.flashcardwebbackend.entity.flashcard.concrete.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, Long> {
}

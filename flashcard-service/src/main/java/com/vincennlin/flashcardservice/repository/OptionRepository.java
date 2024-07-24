package com.vincennlin.flashcardservice.repository;

import com.vincennlin.flashcardservice.entity.flashcard.impl.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, Long> {
}

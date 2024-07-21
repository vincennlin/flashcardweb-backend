package com.vincennlin.flashcardservice.repository;

import com.vincennlin.flashcardservice.entity.impl.Option;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OptionRepository extends JpaRepository<Option, Long> {
}

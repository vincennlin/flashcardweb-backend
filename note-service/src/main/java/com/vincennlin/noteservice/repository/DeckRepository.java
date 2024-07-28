package com.vincennlin.noteservice.repository;

import com.vincennlin.noteservice.entity.Deck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeckRepository extends JpaRepository<Deck, Long> {

    List<Deck> findByUserId(Long userId);

    Boolean existsByNameAndUserId(String name, Long userId);
}

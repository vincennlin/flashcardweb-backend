package com.vincennlin.noteservice.repository;

import com.vincennlin.noteservice.entity.deck.Deck;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeckRepository extends JpaRepository<Deck, Long> {

    List<Deck> findByUserIdAndParent(Long userId, Deck deck);

    Boolean existsByNameAndUserId(String name, Long userId);
}

package com.vincennlin.flashcardservice.payload.deck;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlashcardCountInfo {

    private Map<Long, Integer> noteIdTotalFlashcardCountMap;

    private Map<Long, Integer> noteIdReviewFlashcardCountMap;
}
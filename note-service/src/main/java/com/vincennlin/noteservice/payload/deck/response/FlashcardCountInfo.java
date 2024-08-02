package com.vincennlin.noteservice.payload.deck.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlashcardCountInfo {

    private Map<Long, Integer> noteIdTotalFlashcardCountMap = new HashMap<>();

    private Map<Long, Integer> noteIdReviewFlashcardCountMap = new HashMap<>();
}
package com.vincennlin.aiservice.service;

import com.vincennlin.aiservice.payload.NoteDto;
import com.vincennlin.aiservice.payload.flashcard.FlashcardDto;
import org.springframework.ai.chat.model.ChatResponse;

public interface AiService {

    String generate(String message);

    ChatResponse customGenerate(String message);

    FlashcardDto generateFlashcard(NoteDto noteDto);
}

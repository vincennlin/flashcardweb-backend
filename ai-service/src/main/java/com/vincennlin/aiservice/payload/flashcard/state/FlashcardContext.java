package com.vincennlin.aiservice.payload.flashcard.state;

import com.vincennlin.aiservice.payload.flashcard.dto.AbstractFlashcardDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.ChatOptions;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlashcardContext {

    private FlashcardState flashcardState;

    public Message getSystemMessage() {
        return flashcardState.getSystemMessage();
    }

    public String getFlashcardExampleJsonString() {
        return flashcardState.getFlashcardExampleJsonString();
    }

    public Class<? extends AbstractFlashcardDto> getFlashcardDtoClass() {
        return flashcardState.getFlashcardDtoClass();
    }

    public ChatOptions getChatOptions() {
        return flashcardState.getChatOptions();
    }
}

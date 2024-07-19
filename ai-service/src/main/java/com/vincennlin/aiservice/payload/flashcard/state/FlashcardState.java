package com.vincennlin.aiservice.payload.flashcard.state;

import com.vincennlin.aiservice.payload.flashcard.dto.AbstractFlashcardDto;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.openai.OpenAiChatOptions;

public interface FlashcardState {

    Message getSystemMessage();

    String getFlashcardExampleJsonString();

    Class<? extends AbstractFlashcardDto> getFlashcardDtoClass();

    default ChatOptions getChatOptions() {
        return OpenAiChatOptions.builder()
                .build();
    }
}

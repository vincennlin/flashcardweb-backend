package com.vincennlin.aiservice.payload.flashcard.type;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vincennlin.aiservice.payload.flashcard.dto.AbstractFlashcardDto;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.openai.OpenAiChatOptions;

public interface AbstractFlashcardType {

    Class<? extends AbstractFlashcardDto> getFlashcardDtoClass();

    AbstractFlashcardDto getFlashcardExampleDto();

    String getFormatExampleString();

    default Message getSystemMessage() {
        return new SystemMessage(
                "你會收到一段筆記，根據筆記的content生成一個與範例題型相同的json格式字卡。\n" +
                        getFormatExampleString()
        );
    }

    default String getFlashcardExampleJsonString() {
        try {
            return getObjectMapper().writeValueAsString(getFlashcardExampleDto());
        } catch (Exception e) {
            return "Exception Occurred while Generating Flashcard Example Json String";
        }
    }

    default ChatOptions getChatOptions() {
        return OpenAiChatOptions.builder()
                .build();
    }

    default ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }
}

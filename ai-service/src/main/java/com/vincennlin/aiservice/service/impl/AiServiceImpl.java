package com.vincennlin.aiservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vincennlin.aiservice.exception.JsonFormatException;
import com.vincennlin.aiservice.payload.flashcard.type.FlashcardType;
import com.vincennlin.aiservice.payload.note.NoteDto;
import com.vincennlin.aiservice.payload.flashcard.dto.AbstractFlashcardDto;
import com.vincennlin.aiservice.service.AiService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AiServiceImpl implements AiService {

    private static final Logger logger = LoggerFactory.getLogger(AiServiceImpl.class);

    private final OpenAiChatModel openAiChatModel;

    private Environment env;

    private ObjectMapper objectMapper;

    @Override
    public String generate(String message) {
        return openAiChatModel.call(message);
    }

    @Override
    public AbstractFlashcardDto generateFlashcard(NoteDto noteDto, FlashcardType flashcardType) {

        List<Message> messages = List.of(
                flashcardType.getSystemMessage(),
                new UserMessage(noteDto.getContent())
        );

//        ChatOptions chatOptions = flashcardContext.getChatOptions();

        ChatResponse response = openAiChatModel.call(new Prompt(messages));

        logger.info("response: {}", response.toString());

        String responseContent = response.getResults().get(0).getOutput().getContent();

        return preprocessAndParseJson(responseContent, flashcardType);
    }

    private AbstractFlashcardDto preprocessAndParseJson(String responseContent, FlashcardType flashcardType) {

        if (responseContent.startsWith("```json") && responseContent.endsWith("```")) {
            responseContent = responseContent.substring(7, responseContent.length() - 3).trim();
        }

        try {
            return objectMapper.readValue(responseContent, flashcardType.getFlashcardDtoClass());
        } catch (Exception e) {
            throw new JsonFormatException("Failed to parse response content to FlashcardDto", e.getMessage());
        }
    }
}

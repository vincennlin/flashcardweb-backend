package com.vincennlin.aiservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vincennlin.aiservice.exception.JsonFormatException;
import com.vincennlin.aiservice.payload.evaluate.EvaluateShortAnswerResponse;
import com.vincennlin.aiservice.payload.flashcard.type.FlashcardType;
import com.vincennlin.aiservice.payload.flashcard.dto.FlashcardDto;
import com.vincennlin.aiservice.payload.evaluate.EvaluateShortAnswerRequest;
import com.vincennlin.aiservice.payload.request.GenerateFlashcardRequest;
import com.vincennlin.aiservice.payload.request.GenerateFlashcardsRequest;
import com.vincennlin.aiservice.payload.request.GenerateSummaryRequest;
import com.vincennlin.aiservice.service.AiService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AiServiceImpl implements AiService {

    private static final Logger logger = LoggerFactory.getLogger(AiServiceImpl.class);

    private final OpenAiChatModel openAiChatModel;

    private ObjectMapper objectMapper;

    @Override
    public String generate(String message) {
        return openAiChatModel.call(message);
    }

    @Override
    public String generateSummary(GenerateSummaryRequest request) {

        List<Message> messages = List.of(
                request.getInitialSystemMessage(),
                new UserMessage(request.getNoteContent())
        );

        ChatResponse response = openAiChatModel.call(new Prompt(messages));

        logger.info("response: {}", response.toString());

        return response.getResults().get(0).getOutput().getContent();
    }

    @Override
    public FlashcardDto generateFlashcard(GenerateFlashcardRequest request) {

        List<Message> messages = List.of(
                request.getType().getSystemMessage(),
                new UserMessage(request.getContent())
        );

//        ChatOptions chatOptions = flashcardContext.getChatOptions();

        ChatResponse response = openAiChatModel.call(new Prompt(messages));

        logger.info("response: {}", response.toString());

        String responseContent = response.getResults().get(0).getOutput().getContent();

        return parseGeneratedFlashcardJson(responseContent, request.getType());
    }

    @Override
    public List<FlashcardDto> generateFlashcards(GenerateFlashcardsRequest request) {

        List<Message> messages = List.of(
                request.getInitialSystemMessage(),
                request.getRequirementSystemMessage(),
                request.getResponseFormatExampleSystemMessage(),
                new UserMessage(request.getNote().getContent())
        );

        Prompt prompt = new Prompt(messages);

        ChatResponse response = openAiChatModel.call(prompt);

        logger.info("response: {}", response.toString());

        String responseContent = response.getResults().get(0).getOutput().getContent();

        List<FlashcardDto> generatedFlashcards = parseGeneratedFlashcardsJson(responseContent);

        return generatedFlashcards;
    }

    @Override
    public EvaluateShortAnswerResponse evaluateShortAnswer(EvaluateShortAnswerRequest request) {

        EvaluateShortAnswerResponse evaluateShortAnswerResponse = new EvaluateShortAnswerResponse();

        List<Message> messages = List.of(
                request.getInitialSystemMessage(),
                request.getExampleResponseString(),
                evaluateShortAnswerResponse.getExampleResponseString(),
                new UserMessage(request.toString())
        );

        Prompt prompt = new Prompt(messages);

        ChatResponse response = openAiChatModel.call(prompt);

        logger.info("response: {}", response.toString());

        String responseContent = response.getResults().get(0).getOutput().getContent();

        evaluateShortAnswerResponse = parseEvaluateShortAnswerResponseJson(responseContent);

        return evaluateShortAnswerResponse;
    }

    private FlashcardDto parseGeneratedFlashcardJson(String responseContent, FlashcardType flashcardType) {
        responseContent = preProcessJson(responseContent);
        try {
            return objectMapper.readValue(preProcessJson(responseContent), flashcardType.getFlashcardDtoClass());
        } catch (Exception e) {
            throw new JsonFormatException("Failed to parse response content to FlashcardDto", e.getMessage());
        }
    }

    private List<FlashcardDto> parseGeneratedFlashcardsJson(String responseContent) {
        responseContent = preProcessJson(responseContent);
        try {
            return objectMapper.readValue(preProcessJson(responseContent), objectMapper.getTypeFactory().constructCollectionType(List.class, FlashcardDto.class));
        } catch (Exception e) {
            throw new JsonFormatException("Failed to parse response content to FlashcardDto", e.getMessage());
        }
    }

    private String preProcessJson(String json) {
        if (json.startsWith("```json") && json.endsWith("```")) {
            return json.substring(7, json.length() - 3).trim();
        }
        return json;
    }

    private EvaluateShortAnswerResponse parseEvaluateShortAnswerResponseJson(String responseContent) {
        responseContent = preProcessJson(responseContent);
        try {
            return objectMapper.readValue(preProcessJson(responseContent), EvaluateShortAnswerResponse.class);
        } catch (Exception e) {
            throw new JsonFormatException("Failed to parse response content to EvaluateShortAnswerResponse", e.getMessage());
        }
    }
}

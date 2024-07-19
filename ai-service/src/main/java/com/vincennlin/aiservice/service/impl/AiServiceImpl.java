package com.vincennlin.aiservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vincennlin.aiservice.constant.FlashcardType;
import com.vincennlin.aiservice.payload.NoteDto;
import com.vincennlin.aiservice.payload.flashcard.FlashcardDto;
import com.vincennlin.aiservice.payload.flashcard.concrete.FillInTheBlankFlashcardDto;
import com.vincennlin.aiservice.payload.flashcard.concrete.InBlankAnswerDto;
import com.vincennlin.aiservice.payload.flashcard.concrete.ShortAnswerFlashcardDto;
import com.vincennlin.aiservice.service.AiService;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AiServiceImpl implements AiService {

    private final OpenAiChatModel openAiChatModel;

    private Environment env;

    private ObjectMapper objectMapper;

    @Override
    public String generate(String message) {
        return openAiChatModel.call(message);
    }

    @Override
    public ChatResponse customGenerate(String message) {

        ChatResponse response = openAiChatModel.call(new Prompt(
                message,
                OpenAiChatOptions.builder()
                        .build()
                )
        );

        return null;
    }

    @Override
    public FlashcardDto generateFlashcard(NoteDto noteDto) {

        List<Message> messages = List.of(
                getSystemMessage(),
                new UserMessage(noteDto.getContent())
        );

        ChatOptions chatOptions = OpenAiChatOptions.builder()
//                .withMaxTokens(100)
                .build();

        ChatResponse response = openAiChatModel.call(new Prompt(messages));

        String responseContent = response.getResult().getOutput().getContent();

        FlashcardDto flashcardDto;

        try {
            flashcardDto = objectMapper.readValue(responseContent, FlashcardDto.class);
        } catch (Exception e) {
            e.printStackTrace();
            flashcardDto = new FlashcardDto();
            flashcardDto.setExtraInfo("Failed to generate flashcard");
        }

        return flashcardDto;
    }

    private Message getSystemMessage() {
        String systemPrompt = "你會收到一段筆記，你的任務是根據 content 生成「一個」繁體中文的學習字卡。" +
                "請以下列學習字卡的格式為範本：" + getFillInTheBlankFlashcardTemplateJsonString();
        return new SystemMessage(systemPrompt);
    }

    private String getShortAnswerFlashcardTemplateJsonString() {
        ShortAnswerFlashcardDto shortAnswerFlashcardDto = new ShortAnswerFlashcardDto();
        shortAnswerFlashcardDto.setType(FlashcardType.SHORT_ANSWER);
        shortAnswerFlashcardDto.setQuestion("什麼是 Java ？");
        shortAnswerFlashcardDto.setShortAnswer("Java 是一種物件導向程式語言，由 Sun Microsystems 公司於 1995 年推出。");
        try {
            return objectMapper.writeValueAsString(shortAnswerFlashcardDto);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getFillInTheBlankFlashcardTemplateJsonString() {
        FillInTheBlankFlashcardDto fillInTheBlankFlashcardDto = new FillInTheBlankFlashcardDto();
        fillInTheBlankFlashcardDto.setQuestion("物件導向的四大特性是：封裝、___、___、___。");
        InBlankAnswerDto inBlankAnswerDto1 = new InBlankAnswerDto();
        inBlankAnswerDto1.setText("繼承");
        InBlankAnswerDto inBlankAnswerDto2 = new InBlankAnswerDto();
        inBlankAnswerDto2.setText("多型");
        InBlankAnswerDto inBlankAnswerDto3 = new InBlankAnswerDto();
        inBlankAnswerDto3.setText("抽象");
        fillInTheBlankFlashcardDto.setInBlankAnswers(List.of(inBlankAnswerDto1, inBlankAnswerDto2, inBlankAnswerDto3));
        fillInTheBlankFlashcardDto.setFullAnswer("物件導向的四大特性是：封裝、繼承、多型、抽象。");
        fillInTheBlankFlashcardDto.setType(FlashcardType.FILL_IN_THE_BLANK);
        try {
            return objectMapper.writeValueAsString(fillInTheBlankFlashcardDto);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

package com.vincennlin.aiservice.payload.flashcard.state.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vincennlin.aiservice.payload.flashcard.dto.AbstractFlashcardDto;
import com.vincennlin.aiservice.payload.flashcard.dto.impl.ShortAnswerFlashcardDto;
import com.vincennlin.aiservice.payload.flashcard.state.FlashcardState;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;

public class ShortAnswer implements FlashcardState {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Message getSystemMessage() {
        return new SystemMessage("你會收到一段筆記，請以下列字卡的格式為範本，" +
                "根據筆記的content生成一個json格式的「簡答題」字卡：" + getFlashcardExampleJsonString() +
                "\n請注意，答案請填入簡短的文字描述");
    }

    @Override
    public String getFlashcardExampleJsonString() {

        ShortAnswerFlashcardDto shortAnswerFlashcardDto = new ShortAnswerFlashcardDto();
        shortAnswerFlashcardDto.setQuestion("什麼是 Java ？");

        shortAnswerFlashcardDto.setShortAnswer("Java 是一種物件導向程式語言，由 Sun Microsystems 公司於 1995 年推出。");

        try {
            return objectMapper.writeValueAsString(shortAnswerFlashcardDto);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Class<? extends AbstractFlashcardDto> getFlashcardDtoClass() {
        return ShortAnswerFlashcardDto.class;
    }
}

package com.vincennlin.aiservice.payload.flashcard.state.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vincennlin.aiservice.payload.flashcard.dto.AbstractFlashcardDto;
import com.vincennlin.aiservice.payload.flashcard.dto.impl.TrueFalseFlashcardDto;
import com.vincennlin.aiservice.payload.flashcard.state.FlashcardState;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;

public class TrueFalse implements FlashcardState {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Message getSystemMessage() {
        return new SystemMessage("你會收到一段筆記，請以下列字卡的格式為範本，" +
                "根據筆記的content生成一個json格式的「是非題」字卡：" + getFlashcardExampleJsonString() +
                "\n請注意，答案請填入 true 或 false");
    }

    @Override
    public String getFlashcardExampleJsonString() {

        TrueFalseFlashcardDto trueFalseFlashcardDto = new TrueFalseFlashcardDto();
        trueFalseFlashcardDto.setQuestion("Java 是一種物件導向程式語言。");

        trueFalseFlashcardDto.setTrueFalseAnswer(true);

        try {
            return objectMapper.writeValueAsString(trueFalseFlashcardDto);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Class<? extends AbstractFlashcardDto> getFlashcardDtoClass() {
        return TrueFalseFlashcardDto.class;
    }
}

package com.vincennlin.aiservice.payload.flashcard.state.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vincennlin.aiservice.payload.flashcard.dto.AbstractFlashcardDto;
import com.vincennlin.aiservice.payload.flashcard.dto.impl.FillInTheBlankFlashcardDto;
import com.vincennlin.aiservice.payload.flashcard.dto.impl.InBlankAnswerDto;
import com.vincennlin.aiservice.payload.flashcard.state.FlashcardState;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;

import java.util.List;

public class FillInTheBlank implements FlashcardState {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Message getSystemMessage() {
        return new SystemMessage("你會收到一段筆記，請以下列字卡的格式為範本，" +
                "根據筆記的content生成一個json格式的「填空題」字卡：" + getFlashcardExampleJsonString() +
                "\n請注意，填空的數量最少三個，最多五個。");
    }

    @Override
    public String getFlashcardExampleJsonString() {

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

        try {
            return objectMapper.writeValueAsString(fillInTheBlankFlashcardDto);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Class<? extends AbstractFlashcardDto> getFlashcardDtoClass() {
        return FillInTheBlankFlashcardDto.class;
    }
}

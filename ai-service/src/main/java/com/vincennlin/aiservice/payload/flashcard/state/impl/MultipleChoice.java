package com.vincennlin.aiservice.payload.flashcard.state.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vincennlin.aiservice.payload.flashcard.dto.AbstractFlashcardDto;
import com.vincennlin.aiservice.payload.flashcard.dto.impl.MultipleChoiceFlashcardDto;
import com.vincennlin.aiservice.payload.flashcard.dto.impl.OptionDto;
import com.vincennlin.aiservice.payload.flashcard.state.FlashcardState;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;

import java.util.List;

public class MultipleChoice implements FlashcardState {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Message getSystemMessage() {
        return new SystemMessage("你會收到一段筆記，請以下列字卡的格式為範本，" +
                "根據筆記的content生成一個json格式的「選擇題」字卡：\n" + getFlashcardExampleJsonString() +
                "\n請注意，選項請包含四個，並且答案索引請介於一到四之間");
    }

    @Override
    public String getFlashcardExampleJsonString() {

        MultipleChoiceFlashcardDto multipleChoiceFlashcardDto = new MultipleChoiceFlashcardDto();
        multipleChoiceFlashcardDto.setQuestion("哪一個不是物件導向程式語言的特性？");

        OptionDto optionDto1 = new OptionDto();
        optionDto1.setText("封裝");

        OptionDto optionDto2 = new OptionDto();
        optionDto2.setText("繼承");

        OptionDto optionDto3 = new OptionDto();
        optionDto3.setText("多型");

        OptionDto optionDto4 = new OptionDto();
        optionDto4.setText("抽象");

        multipleChoiceFlashcardDto.setOptions(List.of(optionDto1, optionDto2, optionDto3, optionDto4));
        multipleChoiceFlashcardDto.setAnswerIndex(4);

        try {
            return objectMapper.writeValueAsString(multipleChoiceFlashcardDto);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Class<? extends AbstractFlashcardDto> getFlashcardDtoClass() {
        return MultipleChoiceFlashcardDto.class;
    }
}

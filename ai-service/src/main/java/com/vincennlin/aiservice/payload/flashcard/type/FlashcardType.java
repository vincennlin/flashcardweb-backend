package com.vincennlin.aiservice.payload.flashcard.type;

import com.vincennlin.aiservice.payload.flashcard.dto.FlashcardDto;
import com.vincennlin.aiservice.payload.flashcard.dto.impl.*;
import lombok.Getter;

import java.util.List;

@Getter
public enum FlashcardType implements AbstractFlashcardType {

    SHORT_ANSWER {

        @Override
        public Class<? extends FlashcardDto> getFlashcardDtoClass() {
            return ShortAnswerFlashcardDto.class;
        }

        @Override
        public FlashcardDto getFlashcardExampleDto() {

            ShortAnswerFlashcardDto shortAnswerFlashcardDto = new ShortAnswerFlashcardDto();
            shortAnswerFlashcardDto.setQuestion("什麼是 Java ？");

            shortAnswerFlashcardDto.setShortAnswer("Java 是一種物件導向程式語言，由 Sun Microsystems 公司於 1995 年推出。");

            return shortAnswerFlashcardDto;
        }

        @Override
        public String getFormatExampleString() {
            return "以下是一個「簡答題」字卡的json範例格式：\n" +
                    getFlashcardExampleJsonString() + "\n" +
                    "請注意，答案請簡潔扼要。\n";
        }

        @Override
        public String toString() {
            return "SHORT_ANSWER";
        }
    },

    MULTIPLE_CHOICE{

        @Override
        public Class<? extends FlashcardDto> getFlashcardDtoClass() {
            return MultipleChoiceFlashcardDto.class;
        }

        @Override
        public FlashcardDto getFlashcardExampleDto() {

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

            return multipleChoiceFlashcardDto;
        }

        @Override
        public String getFormatExampleString() {
            return "以下是一個「選擇題」字卡的json範例格式：\n" +
                    getFlashcardExampleJsonString() + "\n" +
                    "請注意，選項請包含四個，且答案索引請介於一至四之間。\n";
        }

        @Override
        public String toString() {
            return "MULTIPLE_CHOICE";
        }
    },

    FILL_IN_THE_BLANK{

        @Override
        public Class<? extends FlashcardDto> getFlashcardDtoClass() {
            return FillInTheBlankFlashcardDto.class;
        }

        @Override
        public FlashcardDto getFlashcardExampleDto() {

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

            return fillInTheBlankFlashcardDto;
        }

        @Override
        public String getFormatExampleString() {
            return "以下是一個「填充題」字卡的json範例格式：\n" +
                    getFlashcardExampleJsonString() + "\n" +
                    "請注意，同一題的空格 (in_blank_answers) 數量請介於 3 ~ 5 個之間，且與題目中的底線（___）數量匹配，這是最重要的要求。" +
                    "答案順序請依照題目空格做排序。\n";
        }

        @Override
        public String toString() {
            return "FILL_IN_THE_BLANK";
        }
    },

    TRUE_FALSE{

        @Override
        public Class<? extends FlashcardDto> getFlashcardDtoClass() {
            return TrueFalseFlashcardDto.class;
        }

        @Override
        public FlashcardDto getFlashcardExampleDto() {

            TrueFalseFlashcardDto trueFalseFlashcardDto = new TrueFalseFlashcardDto();
            trueFalseFlashcardDto.setQuestion("Java 是一種物件導向程式語言。");

            trueFalseFlashcardDto.setTrueFalseAnswer(true);

            return trueFalseFlashcardDto;
        }

        @Override
        public String getFormatExampleString() {
            return "以下是一個「是非題」字卡的json範例格式：\n" +
                    getFlashcardExampleJsonString() + "\n" +
                    "請注意，答案請填入 true 或 false，請平均分配 true 與 false 出現的頻率。\n";
        }

        @Override
        public String toString() {
            return "TRUE_FALSE";
        }
    };
}
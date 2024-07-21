package com.vincennlin.flashcardservice.payload.flashcard.type;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vincennlin.flashcardservice.entity.AbstractFlashcard;
import com.vincennlin.flashcardservice.entity.impl.FillInTheBlankFlashcard;
import com.vincennlin.flashcardservice.entity.impl.MultipleChoiceFlashcard;
import com.vincennlin.flashcardservice.entity.impl.ShortAnswerFlashcard;
import com.vincennlin.flashcardservice.entity.impl.TrueFalseFlashcard;
import com.vincennlin.flashcardservice.payload.flashcard.dto.AbstractFlashcardDto;
import com.vincennlin.flashcardservice.payload.flashcard.dto.impl.*;
import lombok.Getter;

import java.util.List;

@Getter
public enum FlashcardType implements AbstractFlashcardType {

    MULTIPLE_CHOICE{

        @Override
        public AbstractFlashcardDto getFlashcardExampleDto() {

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
        public Class<? extends AbstractFlashcardDto> getFlashcardDtoClass() {
            return MultipleChoiceFlashcardDto.class;
        }

        @Override
        public Class<? extends AbstractFlashcard> getFlashcardEntityClass() {
            return MultipleChoiceFlashcard.class;
        }

        @Override
        public String toString() {
            return "MULTIPLE_CHOICE";
        }
    },

    TRUE_FALSE{

        @Override
        public AbstractFlashcardDto getFlashcardExampleDto() {

            TrueFalseFlashcardDto trueFalseFlashcardDto = new TrueFalseFlashcardDto();
            trueFalseFlashcardDto.setQuestion("Java 是一種物件導向程式語言。");

            trueFalseFlashcardDto.setTrueFalseAnswer(true);

            return trueFalseFlashcardDto;
        }

        @Override
        public Class<? extends AbstractFlashcardDto> getFlashcardDtoClass() {
            return TrueFalseFlashcardDto.class;
        }

        @Override
        public Class<? extends AbstractFlashcard> getFlashcardEntityClass() {
            return TrueFalseFlashcard.class;
        }

        @Override
        public String toString() {
            return "TRUE_FALSE";
        }
    },

    FILL_IN_THE_BLANK{

        @Override
        public AbstractFlashcardDto getFlashcardExampleDto() {

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
        public Class<? extends AbstractFlashcardDto> getFlashcardDtoClass() {
            return FillInTheBlankFlashcardDto.class;
        }

        @Override
        public Class<? extends AbstractFlashcard> getFlashcardEntityClass() {
            return FillInTheBlankFlashcard.class;
        }

        @Override
        public String toString() {
            return "FILL_IN_THE_BLANK";
        }
    },

    SHORT_ANSWER {

        @Override
        public AbstractFlashcardDto getFlashcardExampleDto() {

            ShortAnswerFlashcardDto shortAnswerFlashcardDto = new ShortAnswerFlashcardDto();
            shortAnswerFlashcardDto.setQuestion("什麼是 Java ？");

            shortAnswerFlashcardDto.setShortAnswer("Java 是一種物件導向程式語言，由 Sun Microsystems 公司於 1995 年推出。");

            return shortAnswerFlashcardDto;
        }

        @Override
        public Class<? extends AbstractFlashcardDto> getFlashcardDtoClass() {
            return ShortAnswerFlashcardDto.class;
        }

        @Override
        public Class<? extends AbstractFlashcard> getFlashcardEntityClass() {
            return ShortAnswerFlashcard.class;
        }

        @Override
        public String toString() {
            return "SHORT_ANSWER";
        }
    };

    private static final ObjectMapper objectMapper = new ObjectMapper();
}
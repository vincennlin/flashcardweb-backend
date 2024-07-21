package com.vincennlin.aiservice.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vincennlin.aiservice.payload.flashcard.dto.AbstractFlashcardDto;
import com.vincennlin.aiservice.payload.flashcard.type.FlashcardType;
import com.vincennlin.aiservice.payload.note.NoteDto;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GenerateFlashcardsRequest {

    @JsonProperty(value = "note")
    private NoteDto note;

    @NotEmpty(message = "Type quantities cannot be empty")
    @JsonProperty(value = "type_quantities")
    private List<TypeQuantity> typeQuantities;

    public Message getInitialSystemMessage() {
        return new SystemMessage(
                "你的任務是根據收到的筆記，生成「簡答題」、「填充題」、「選擇題」、「是非題」四種題型的字卡。\n" +
                        "你會收到一個筆記的content，請先參考以下各種題型的json範例格式，並在收到content後，生成" +
                        "簡答題字卡" + getQuantityFor(FlashcardType.SHORT_ANSWER) + "張、" +
                        "填充題字卡" + getQuantityFor(FlashcardType.FILL_IN_THE_BLANK) + "張、" +
                        "選擇題字卡" + getQuantityFor(FlashcardType.MULTIPLE_CHOICE) + "張、" +
                        "是非題字卡" + getQuantityFor(FlashcardType.TRUE_FALSE) + "張。\n" +
                        "並以json陣列的格式回傳字卡。\n\n" +
                        FlashcardType.SHORT_ANSWER.getFormatExampleString() + "\n\n" +
                        FlashcardType.FILL_IN_THE_BLANK.getFormatExampleString() + "\n\n" +
                        FlashcardType.MULTIPLE_CHOICE.getFormatExampleString() + "\n\n" +
                        FlashcardType.TRUE_FALSE.getFormatExampleString() + "\n\n"
        );
    }

    public Message getResponseFormatExampleSystemMessage() {
        return new SystemMessage(
                "以下是一個範例的json格式回應：\n\n" +
                        getResponseExampleString() + "\n\n" +
                        "請注意，以上json回應僅為格式範例，實際生成的字卡內容請依照收到的content生成。\n\n"
        );
    }

    private String getResponseExampleString() {
        List<AbstractFlashcardDto> exampleFlashcards = new ArrayList<>();
        for (int i = 0; i < getQuantityFor(FlashcardType.SHORT_ANSWER); i++) {
            exampleFlashcards.add(FlashcardType.SHORT_ANSWER.getFlashcardExampleDto());
        }
        for (int i = 0; i < getQuantityFor(FlashcardType.FILL_IN_THE_BLANK); i++) {
            exampleFlashcards.add(FlashcardType.FILL_IN_THE_BLANK.getFlashcardExampleDto());
        }
        for (int i = 0; i < getQuantityFor(FlashcardType.MULTIPLE_CHOICE); i++) {
            exampleFlashcards.add(FlashcardType.MULTIPLE_CHOICE.getFlashcardExampleDto());
        }
        for (int i = 0; i < getQuantityFor(FlashcardType.TRUE_FALSE); i++) {
            exampleFlashcards.add(FlashcardType.TRUE_FALSE.getFlashcardExampleDto());
        }
        try{
            return getObjectMapper().writeValueAsString(exampleFlashcards);
        } catch (Exception e) {
            return "Exception Occurred while Generating Flashcards Example Json String";
        }
    }

    private Integer getQuantityFor(FlashcardType flashcardType) {
        return typeQuantities.stream()
                .filter(typeQuantity -> typeQuantity.getType().equals(flashcardType))
                .findFirst()
                .map(TypeQuantity::getQuantity)
                .orElse(0);
    }

    private ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }
}

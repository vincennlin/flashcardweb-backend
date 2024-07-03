package com.vincennlin.flashcardwebbackend.entity.flashcard.concrete;

import com.vincennlin.flashcardwebbackend.constant.FlashcardType;
import com.vincennlin.flashcardwebbackend.entity.flashcard.Flashcard;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "short_answers")
public class ShortAnswerFlashcard extends Flashcard {

    @Column(name = "short_answer")
    private String answer;
}

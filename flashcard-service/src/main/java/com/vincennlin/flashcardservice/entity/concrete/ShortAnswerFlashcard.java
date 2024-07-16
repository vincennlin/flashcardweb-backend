package com.vincennlin.flashcardservice.entity.concrete;

import com.vincennlin.flashcardservice.entity.Flashcard;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
    private String shortAnswer;
}

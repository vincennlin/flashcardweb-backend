package com.vincennlin.flashcardwebbackend.entity.flashcard.concrete;

import com.vincennlin.flashcardwebbackend.entity.flashcard.Flashcard;
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
@Table(name = "true_false_flashcards")
public class TrueFalseFlashcard extends Flashcard {

    private boolean answer;
}

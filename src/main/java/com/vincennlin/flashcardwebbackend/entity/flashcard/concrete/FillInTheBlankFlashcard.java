package com.vincennlin.flashcardwebbackend.entity.flashcard.concrete;

import com.vincennlin.flashcardwebbackend.entity.flashcard.BlankAnswer;
import com.vincennlin.flashcardwebbackend.entity.flashcard.Flashcard;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "fill_in_the_blank_flashcards")
public class FillInTheBlankFlashcard extends Flashcard {

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "flashcard")
    @Column(name = "blank_answers")
    private List<BlankAnswer> blankAnswers;

    @Column(name = "full_answer")
    private String fullAnswer;
}

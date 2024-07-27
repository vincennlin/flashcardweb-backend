package com.vincennlin.flashcardservice.entity.flashcard.impl;

import com.vincennlin.flashcardservice.entity.flashcard.Flashcard;
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
@Table(name = "multiple_choice")
public class MultipleChoiceFlashcard extends Flashcard {

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER,
            mappedBy = "flashcard", orphanRemoval = true)
    private List<Option> options;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "answer_option_id")
    private Option answerOption;
}


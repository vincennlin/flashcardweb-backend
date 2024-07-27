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
@Table(name = "fill_in_the_blank")
public class FillInTheBlankFlashcard extends Flashcard {

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER,
            mappedBy = "flashcard", orphanRemoval = true)
    @Column(name = "in_blank_answers")
    private List<InBlankAnswer> inBlankAnswers;

    @Column(name = "full_answer", length = 3000)
    private String fullAnswer;
}

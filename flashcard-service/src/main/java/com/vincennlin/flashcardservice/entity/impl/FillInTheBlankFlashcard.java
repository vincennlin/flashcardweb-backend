package com.vincennlin.flashcardservice.entity.impl;

import com.vincennlin.flashcardservice.entity.AbstractFlashcard;
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
public class FillInTheBlankFlashcard extends AbstractFlashcard {

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "flashcard")
    @Column(name = "in_blank_answers")
    private List<InBlankAnswer> inBlankAnswers;

    @Column(name = "full_answer")
    private String fullAnswer;
}

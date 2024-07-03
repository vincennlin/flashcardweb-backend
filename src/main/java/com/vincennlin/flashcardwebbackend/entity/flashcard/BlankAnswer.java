package com.vincennlin.flashcardwebbackend.entity.flashcard;

import com.vincennlin.flashcardwebbackend.entity.flashcard.concrete.FillInTheBlankFlashcard;
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
@Table(name = "blank_answers")
public class BlankAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flashcard_id")
    private FillInTheBlankFlashcard flashcard;
}

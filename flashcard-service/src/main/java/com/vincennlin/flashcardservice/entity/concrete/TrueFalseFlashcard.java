package com.vincennlin.flashcardservice.entity.concrete;

import com.vincennlin.flashcardservice.entity.AbstractFlashcard;
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
@Table(name = "true_false_answers")
public class TrueFalseFlashcard extends AbstractFlashcard {

    @Column(name = "true_false_answer")
    private boolean trueFalseAnswer;
}

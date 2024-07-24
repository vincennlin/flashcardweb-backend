package com.vincennlin.flashcardservice.entity.tag;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.vincennlin.flashcardservice.entity.flashcard.AbstractFlashcard;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "tags",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"tag_name", "user_id"}
        )
)
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tag_name")
    private String tagName;

    @ManyToMany(
            mappedBy = "tags",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY
    )
    @JsonBackReference
    private List<AbstractFlashcard> flashcards = new ArrayList<>();

    @Column(name = "user_id", nullable = false, updatable = false)
    private Long userId;
}
package com.vincennlin.flashcardwebbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "flashcards")
public class Flashcard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;
    private String answer;

    @Column(name = "extra_info")
    private String extraInfo;

    @CreationTimestamp
    @Column(name = "date_created")
    private String dateCreated;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private String lastUpdated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "note_id")
    private Note note;
}

package com.vincennlin.flashcardservice.entity;

import com.vincennlin.flashcardservice.constant.FlashcardType;
import com.vincennlin.userservice.entity.User;
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
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Flashcard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;

    @Column(name = "extra_info")
    private String extraInfo;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private FlashcardType type;

    @Column(name = "user_id", nullable = false, updatable = false)
    private Long userId;

    @Column(name = "note_id", nullable = false, updatable = false)
    private Long noteId;

    @CreationTimestamp
    @Column(name = "date_created")
    private String dateCreated;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private String lastUpdated;
}

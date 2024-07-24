package com.vincennlin.flashcardservice.entity.flashcard;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.vincennlin.flashcardservice.entity.tag.Tag;
import com.vincennlin.flashcardservice.payload.flashcard.type.FlashcardType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "flashcards")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class AbstractFlashcard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 3000)
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

    @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "flashcards_tags",
            joinColumns = @JoinColumn(name = "flashcard_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @JsonManagedReference
    private List<Tag> tags;

    @CreationTimestamp
    @Column(name = "date_created")
    private String dateCreated;

    @UpdateTimestamp
    @Column(name = "last_updated")
    private String lastUpdated;
}

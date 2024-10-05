package com.vincennlin.courseservice.entity;

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
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_id", nullable = false)
    private String name;

    @Column(name = "creator_id")
    private Long creatorId;

    @ElementCollection
    @CollectionTable(name = "course_users", joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "user_id")
    private List<Long> userIds;

    @ElementCollection
    @CollectionTable(name = "course_flashcards", joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "flashcard_id")
    private List<Long> flashcardIds;
}

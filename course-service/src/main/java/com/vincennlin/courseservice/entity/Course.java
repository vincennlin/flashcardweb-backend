package com.vincennlin.courseservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "courses")
public class Course {

    public Course(String name, Long creatorId) {
        this.name = name;
        this.creatorId = creatorId;
        this.userIds = Set.of(creatorId);
        this.flashcardIds = new HashSet<>();
    }

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
    private Set<Long> userIds;

    @ElementCollection
    @CollectionTable(name = "course_flashcards", joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "flashcard_id")
    private Set<Long> flashcardIds;

    public int getUserCount() {
        if (userIds == null) {
            return 0;
        }
        return userIds.size();
    }

    public int getFlashcardCount() {
        if (flashcardIds == null) {
            return 0;
        }
        return flashcardIds.size();
    }

    public boolean containsUser(Long userId) {
        return userIds.contains(userId);
    }

    public void addUser(Long userId) {
        userIds.add(userId);
    }

    public void removeUser(Long userId) {
        userIds.remove(userId);
    }

    public void addFlashcardIds(List<Long> flashcardIds) {
        this.flashcardIds.addAll(flashcardIds);
    }

    public void removeFlashcardIds(List<Long> flashcardIds) {
        flashcardIds.forEach(this.flashcardIds::remove);
    }
}

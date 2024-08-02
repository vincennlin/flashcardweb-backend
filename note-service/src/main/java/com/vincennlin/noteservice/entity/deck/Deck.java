package com.vincennlin.noteservice.entity.deck;

import com.vincennlin.noteservice.entity.note.Note;
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
@Table(name = "decks")
public class Deck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    private Deck parent;

    @OneToMany(mappedBy = "parent",
            cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Deck> subDecks = new HashSet<>();

    @OneToMany(mappedBy = "deck", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Note> notes = new HashSet<>();

    public void addNote(Note note) {
        notes.add(note);
        note.setDeck(this);
    }

    public int getTotalNotes() {
        int total = notes.size();
        for (Deck subDeck : subDecks) {
            total += subDeck.getTotalNotes();
        }
        return total;
    }
}

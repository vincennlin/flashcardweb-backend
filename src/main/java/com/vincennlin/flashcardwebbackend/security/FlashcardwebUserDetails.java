package com.vincennlin.flashcardwebbackend.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class FlashcardwebUserDetails extends User {

    private final String email;
    private final Long userId;

    public FlashcardwebUserDetails(String username, String email, String password, Collection<? extends GrantedAuthority> authorities, Long userId) {
        super(username, password, authorities);
        this.email = email;
        this.userId = userId;
    }
}
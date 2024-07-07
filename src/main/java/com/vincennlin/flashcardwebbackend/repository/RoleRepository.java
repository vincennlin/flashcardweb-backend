package com.vincennlin.flashcardwebbackend.repository;

import com.vincennlin.flashcardwebbackend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}

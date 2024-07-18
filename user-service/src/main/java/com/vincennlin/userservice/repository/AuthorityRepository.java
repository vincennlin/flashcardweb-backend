package com.vincennlin.userservice.repository;

import com.vincennlin.userservice.entity.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    Authority findByName(String name);
}

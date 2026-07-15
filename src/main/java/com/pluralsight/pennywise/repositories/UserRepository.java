package com.pluralsight.pennywise.repositories;

import com.pluralsight.pennywise.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    // It looks for a User row where the username column matches.
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
package com.pluralsight.pennywise.repositories;

import com.pluralsight.pennywise.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}

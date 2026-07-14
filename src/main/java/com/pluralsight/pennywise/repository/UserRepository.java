package com.pluralsight.pennywise.repository;

import com.pluralsight.pennywise.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}

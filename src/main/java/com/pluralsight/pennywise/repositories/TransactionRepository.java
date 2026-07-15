package com.pluralsight.pennywise.repositories;

import com.pluralsight.pennywise.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository
        extends JpaRepository<Transaction, Integer> {

    List<Transaction> findByUserId(UUID userId);
}
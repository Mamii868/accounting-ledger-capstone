package com.pluralsight.pennywise.repository;

import com.pluralsight.pennywise.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}

package com.pluralsight.pennywise.controllers;

import com.pluralsight.pennywise.models.Transaction;
import com.pluralsight.pennywise.services.TransactionService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transactions")
@CrossOrigin
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    private boolean isAdmin(HttpSession session) {
        return "ADMIN".equals(session.getAttribute("role"));
    }

    // Get the logged-in user's transactions
    @GetMapping
    public ResponseEntity<List<Transaction>> getMyTransactions(HttpSession session) {
        UUID userId = (UUID) session.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        return ResponseEntity.ok(transactionService.getTransactionsByUserId(userId));
    }

    // ADMIN ONLY: get every transaction in the system
    @GetMapping("/all")
    public ResponseEntity<List<Transaction>> getAllTransactions(HttpSession session) {
        if (!isAdmin(session)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(transactionService.getAllTransactions());
    }

    // Get transaction by ID
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable int id) {
        Transaction transaction = transactionService.getTransactionById(id);

        if (transaction == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(transaction);
    }

    // ADMIN ONLY: get all transactions for a specific user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Transaction>> getTransactionsByUserId(@PathVariable UUID userId,
                                                                     HttpSession session) {
        if (!isAdmin(session)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(transactionService.getTransactionsByUserId(userId));
    }

    // Create a new transaction for the logged-in user
    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@Valid @RequestBody Transaction transaction,
                                                         HttpSession session) {
        UUID userId = (UUID) session.getAttribute("userId");

        if (userId == null) {
            // not logged in
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        // always taken from the session, never from the request body
        transaction.setUserId(userId);

        Transaction createdTransaction = transactionService.createTransaction(transaction);
        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
    }

    // Update an existing transaction
    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransaction(
            @PathVariable int id,
            @Valid @RequestBody Transaction transaction) {

        Transaction updatedTransaction = transactionService.updateTransaction(id, transaction);

        if (updatedTransaction == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(updatedTransaction);
    }

    // Delete a transaction
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable int id) {

        boolean deleted = transactionService.deleteTransaction(id);

        if (!deleted) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }
}

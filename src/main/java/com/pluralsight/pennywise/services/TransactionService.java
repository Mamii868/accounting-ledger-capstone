package com.pluralsight.pennywise.services;

import com.pluralsight.pennywise.models.Transaction;
import com.pluralsight.pennywise.repositories.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public List<Transaction> getAllTransactions() {
        return transactionRepository.findAll();
    }

    public Transaction getTransactionById(int id) {
        return transactionRepository.findById(id).orElse(null);
    }

    public List<Transaction> getTransactionsByUserId(UUID userId) {
        return transactionRepository.findByUserId(userId);
    }

    public Transaction createTransaction(Transaction transaction) {
        transaction.setId(0);

        // always server-generated, never trust client input for this
        String ref = "TXN-" + LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE)
                + "-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        transaction.setReference(ref);

        if (transaction.getCreatedAt() == null) {
            transaction.setCreatedAt(LocalDateTime.now());
        }

        return transactionRepository.save(transaction);
    }

    public Transaction updateTransaction(int id, Transaction transaction) {
        Optional<Transaction> optionalTransaction =
                transactionRepository.findById(id);

        if (optionalTransaction.isEmpty()) {
            return null;
        }

        Transaction existingTransaction = optionalTransaction.get();

        existingTransaction.setDescription(transaction.getDescription());
        existingTransaction.setVendor(transaction.getVendor());
        existingTransaction.setAmount(transaction.getAmount());
        existingTransaction.setUserId(transaction.getUserId());

        if (transaction.getCreatedAt() != null) {
            existingTransaction.setCreatedAt(transaction.getCreatedAt());
        }

        return transactionRepository.save(existingTransaction);
    }

    public boolean deleteTransaction(int id) {
        if (!transactionRepository.existsById(id)) {
            return false;
        }

        transactionRepository.deleteById(id);
        return true;
    }
}

package com.pluralsight.pennywise.service;

import com.pluralsight.pennywise.models.Transaction;
import com.pluralsight.pennywise.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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

    public List<Transaction> getTransactionsByUserId(int userId) {
        return transactionRepository.findByUserId(userId);
    }

    public Transaction createTransaction(Transaction transaction) {
        transaction.setId(0);

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

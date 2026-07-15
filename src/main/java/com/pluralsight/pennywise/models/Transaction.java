package com.pluralsight.pennywise.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private int id;

    @NotBlank
    @Size(max = 255)
    @Column(name = "description")
    private String description;

    @NotBlank
    @Size(max = 100)
    @Column(name = "vendor")
    private String vendor;

    @Column(name = "amount")
    private double amount;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Transaction() {
    }

    public Transaction(int id, String description, String vendor, double amount, int userId, LocalDateTime createdAt) {
        this.id = id;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
        this.userId = userId;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

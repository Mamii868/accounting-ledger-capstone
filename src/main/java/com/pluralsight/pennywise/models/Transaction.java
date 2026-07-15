package com.pluralsight.pennywise.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transactionID")
    private int id;

    @NotBlank
    @Size(max = 255)
    @Column(name = "description")
    private String description;

    @NotBlank
    @Size(max = 100)
    @Column(name = "vendor")
    private String vendor;

    @NotNull
    @Digits(integer = 8, fraction = 2)
    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(name = "userID")
    private UUID userId;

    @Column(name = "timestamp")
    private LocalDateTime createdAt;
    @Column( name = "reference", unique = true, updatable = false)
    private String reference;

    public Transaction() {
    }

    public Transaction(int id, String description, String vendor, BigDecimal amount, UUID userId, LocalDateTime createdAt) {
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

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}

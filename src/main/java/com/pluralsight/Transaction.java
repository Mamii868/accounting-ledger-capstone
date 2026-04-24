package com.pluralsight;

import java.time.LocalDate;
import java.time.LocalTime;

public class Transaction {
    private LocalDate date;
    private LocalTime time;
    private String name;
    private String entity;
    private double amount;

    public Transaction(LocalDate date, LocalTime time, String name, String entity, double amount) {
        this.date = date;
        this.time = time;
        this.name = name;
        this.entity = entity;
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public String getEntity() {
        return entity;
    }

    public double getAmount() {
        return amount;
    }
}

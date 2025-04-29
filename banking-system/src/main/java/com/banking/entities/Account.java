package com.banking.entities;

import jakarta.persistence.*;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Alterado de accountNumber para id

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    private String accountType;
    private Double balance;

    public Account() {
    }

    public Account(Long id, Customer customer, String accountType, Double balance) {
        this.id = id;
        this.customer = customer;
        this.accountType = accountType;
        this.balance = balance;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Manter compatibilidade com o código existente
    public Long getAccountNumber() {
        return id;
    }

    public void setAccountNumber(Long accountNumber) {
        this.id = accountNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
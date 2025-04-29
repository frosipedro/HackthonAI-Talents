package com.banking.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Alterado de transactionId para id

    @ManyToOne
    @JoinColumn(name = "account_id") // Alterado para refletir o novo nome do campo
    private Account account;

    private String type; // e.g., "DEPOSIT" or "WITHDRAW"
    private Double amount;
    private LocalDateTime date;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // Manter compatibilidade com o código existente
    public Long getTransactionId() {
        return id;
    }

    public void setTransactionId(Long transactionId) {
        this.id = transactionId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
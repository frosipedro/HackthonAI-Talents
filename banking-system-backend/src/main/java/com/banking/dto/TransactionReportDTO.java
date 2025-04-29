package com.banking.dto;

public class TransactionReportDTO {

    private String date; // Formato: YYYY/MM/DD
    private String accountType; // Tipo da conta (S ou C)
    private String transactionType; // Tipo da transação (D ou W)
    private Double amount; // Valor formatado com separadores de milhar e 2 casas decimais

    public TransactionReportDTO(String date, String accountType, String transactionType, Double amount) {
        this.date = date;
        this.accountType = accountType;
        this.transactionType = transactionType;
        this.amount = amount;
    }

    // Getters e Setters
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
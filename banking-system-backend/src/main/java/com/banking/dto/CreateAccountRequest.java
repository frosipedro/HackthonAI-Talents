package com.banking.dto;

import jakarta.validation.constraints.NotNull;

enum AccountType {
    C,
    S
}

public class CreateAccountRequest {

    @NotNull(message = "Customer ID is mandatory")
    private Long customerId;

    @NotNull(message = "Account type is mandatory")
    private String accountType;

    // Getters and Setters
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        if (accountType == null
                || (!accountType.equals(AccountType.C.name()) && !accountType.equals(AccountType.S.name()))) {
            throw new IllegalArgumentException("Invalid account type. Must be 'C' or 'S'.");
        }
        this.accountType = accountType;
    }
}
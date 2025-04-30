package com.banking.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class CreateAccountRequest {

    @NotNull(message = "Customer ID is mandatory")
    private Long customerId;

    @NotNull(message = "Account type is mandatory")
    @Pattern(regexp = "^[CS]$", message = "Account type must be 'C' or 'S'")
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
        this.accountType = accountType;
    }
}
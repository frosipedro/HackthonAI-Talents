package com.banking.controllers;

import com.banking.dto.CreateAccountRequest;
import com.banking.dto.DepositRequest;
import com.banking.dto.WithdrawRequest;
import com.banking.entities.Account;
import com.banking.services.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody @Valid CreateAccountRequest request) {
        Account createdAccount = accountService.createAccount(request.getCustomerId(), request.getAccountType());
        return ResponseEntity.ok(createdAccount);
    }

    @PostMapping("/deposit")
    public ResponseEntity<Account> deposit(@RequestBody @Valid DepositRequest request) {
        Account updatedAccount = accountService.deposit(request.getAccountId(), request.getAmount());
        return ResponseEntity.ok(updatedAccount);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<Account> withdraw(@RequestBody @Valid WithdrawRequest request) {
        Account updatedAccount = accountService.withdraw(request.getAccountId(), request.getAmount());
        return ResponseEntity.ok(updatedAccount);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Account>> getAccountsByCustomerId(@PathVariable Long customerId) {
        List<Account> accounts = accountService.getAccountsByCustomerId(customerId);
        return ResponseEntity.ok(accounts);
    }
}
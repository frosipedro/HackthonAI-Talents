package com.banking.controllers;

import com.banking.entities.Account;
import com.banking.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        Account createdAccount = accountService.createAccount(account);
        return ResponseEntity.ok(createdAccount);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Account>> getAccountsByCustomerId(@PathVariable Long customerId) {
        List<Account> accounts = accountService.getAccountsByCustomerId(customerId);
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/{accountId}/balance")
    public ResponseEntity<Double> checkAccountBalance(@PathVariable Long accountId) {
        Double balance = accountService.checkAccountBalance(accountId);
        return ResponseEntity.ok(balance);
    }
}
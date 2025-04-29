package com.banking.controllers;

import com.banking.dto.CreateAccountRequest;
import com.banking.entities.Account;
import com.banking.services.AccountService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
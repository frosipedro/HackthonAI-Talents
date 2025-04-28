package com.banking.account.controller;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.banking.account.model.Account;
import com.banking.account.service.AccountService;

class AccountControllerTest {

    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountService accountService;

    private Account account;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        account = new Account();
        account.setId(1L);
        account.setAccountNumber("123456");
        account.setAccountType("SAVINGS");
        account.setBalance(1000.0);
    }

    @Test
    void testGetAllAccounts() {
        when(accountService.findAllAccounts()).thenReturn(Collections.singletonList(account));

        ResponseEntity<List<Account>> response = accountController.getAllAccounts();

        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        verify(accountService, times(1)).findAllAccounts();
    }

    @Test
    void testGetAccountById() {
        when(accountService.findAccountById(1L)).thenReturn(account);

        ResponseEntity<Account> response = accountController.getAccountById(1L);

        assertNotNull(response.getBody());
        assertEquals(account.getAccountNumber(), response.getBody().getAccountNumber());
        verify(accountService, times(1)).findAccountById(1L);
    }

    @Test
    void testCreateAccount() {
        when(accountService.createAccount(any(Account.class))).thenReturn(account);

        ResponseEntity<Account> response = accountController.createAccount(account);

        assertNotNull(response.getBody());
        assertEquals(account.getAccountNumber(), response.getBody().getAccountNumber());
        verify(accountService, times(1)).createAccount(any(Account.class));
    }

    @Test
    void testDeleteAccount() {
        doNothing().when(accountService).deleteAccount(1L);

        ResponseEntity<Void> response = accountController.deleteAccount(1L);

        assertNotNull(response);
        verify(accountService, times(1)).deleteAccount(1L);
    }
}
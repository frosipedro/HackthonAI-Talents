package com.banking.account.controller;

import com.banking.account.model.Account;
import com.banking.account.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
        account.setBalance(1000.0);
    }

    @Test
    void testGetAllAccounts() {
        when(accountService.getAllAccounts()).thenReturn(Collections.singletonList(account));

        var result = accountController.getAllAccounts();

        assertEquals(1, result.size());
        verify(accountService, times(1)).getAllAccounts();
    }

    @Test
    void testGetAccountById() {
        when(accountService.getAccountById(1L)).thenReturn(Optional.of(account));

        var result = accountController.getAccountById(1L);

        assertTrue(result.isPresent());
        assertEquals(account.getAccountNumber(), result.get().getAccountNumber());
        verify(accountService, times(1)).getAccountById(1L);
    }

    @Test
    void testCreateAccount() {
        when(accountService.createAccount(any(Account.class))).thenReturn(account);

        var result = accountController.createAccount(account);

        assertEquals(account.getAccountNumber(), result.getAccountNumber());
        verify(accountService, times(1)).createAccount(any(Account.class));
    }

    @Test
    void testUpdateAccount() {
        when(accountService.updateAccount(any(Long.class), any(Account.class))).thenReturn(Optional.of(account));

        var result = accountController.updateAccount(1L, account);

        assertTrue(result.isPresent());
        assertEquals(account.getAccountNumber(), result.get().getAccountNumber());
        verify(accountService, times(1)).updateAccount(any(Long.class), any(Account.class));
    }

    @Test
    void testDeleteAccount() {
        doNothing().when(accountService).deleteAccount(1L);

        accountController.deleteAccount(1L);

        verify(accountService, times(1)).deleteAccount(1L);
    }
}
package com.banking.account.service;

import com.banking.account.model.Account;
import com.banking.account.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

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
    void testCreateAccount() {
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        Account createdAccount = accountService.createAccount(account);

        assertNotNull(createdAccount);
        assertEquals(account.getAccountNumber(), createdAccount.getAccountNumber());
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void testGetAccountById() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        Optional<Account> foundAccount = accountService.getAccountById(1L);

        assertTrue(foundAccount.isPresent());
        assertEquals(account.getAccountNumber(), foundAccount.get().getAccountNumber());
        verify(accountRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateAccount() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));
        when(accountRepository.save(any(Account.class))).thenReturn(account);

        account.setBalance(1500.0);
        Account updatedAccount = accountService.updateAccount(1L, account);

        assertNotNull(updatedAccount);
        assertEquals(1500.0, updatedAccount.getBalance());
        verify(accountRepository, times(1)).save(account);
    }

    @Test
    void testDeleteAccount() {
        when(accountRepository.findById(1L)).thenReturn(Optional.of(account));

        accountService.deleteAccount(1L);

        verify(accountRepository, times(1)).delete(account);
    }
}
package com.banking.services;

import com.banking.entities.Account;
import com.banking.entities.Transaction;
import com.banking.repositories.AccountRepository;
import com.banking.repositories.CustomerRepository;
import com.banking.repositories.TransactionRepository;
import com.banking.utils.exception.NotFoundException;
import com.banking.utils.exception.ValidationException;
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

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private AccountService accountService;

    private Account testAccount;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testAccount = new Account();
        testAccount.setId(1L);
        testAccount.setCustomerId(1L);
        testAccount.setAccountType("S");
        testAccount.setBalance(1000.0);
    }

    @Test
    void createAccount_Success() {
        when(customerRepository.existsById(1L)).thenReturn(true);
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> {
            Account savedAccount = invocation.getArgument(0);
            savedAccount.setId(1L);
            return savedAccount;
        });

        Account result = accountService.createAccount(1L, "S");

        assertNotNull(result);
        assertEquals(1L, result.getCustomerId());
        assertEquals("S", result.getAccountType());
        assertEquals(0.0, result.getBalance());
        verify(accountRepository).save(any(Account.class));
    }

    @Test
    void createAccount_CustomerNotFound() {
        when(customerRepository.existsById(1L)).thenReturn(false);

        assertThrows(IllegalArgumentException.class, () -> {
            accountService.createAccount(1L, "S");
        });

        verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void deposit_Success() {
        Double initialBalance = 1000.0;
        Double depositAmount = 500.0;

        when(accountRepository.findById(1L)).thenReturn(Optional.of(testAccount));
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> {
            Account savedAccount = invocation.getArgument(0);
            testAccount.setBalance(savedAccount.getBalance());
            return testAccount;
        });

        Account result = accountService.deposit(1L, depositAmount);

        assertNotNull(result);
        assertEquals(initialBalance + depositAmount, result.getBalance());
        verify(accountRepository).save(any(Account.class));
        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    void deposit_AccountNotFound() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            accountService.deposit(1L, 500.0);
        });

        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void deposit_InvalidAmount() {
        assertThrows(ValidationException.class, () -> {
            accountService.deposit(1L, -100.0);
        });

        verify(accountRepository, never()).save(any(Account.class));
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void withdraw_Success() {
        Double initialBalance = 1000.0;
        Double withdrawAmount = 500.0;

        when(accountRepository.findById(1L)).thenReturn(Optional.of(testAccount));
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> {
            Account savedAccount = invocation.getArgument(0);
            testAccount.setBalance(savedAccount.getBalance());
            return testAccount;
        });

        Account result = accountService.withdraw(1L, withdrawAmount);

        assertNotNull(result);
        assertEquals(initialBalance - withdrawAmount, result.getBalance());
        verify(accountRepository).save(any(Account.class));
        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    void withdraw_InsufficientBalance() {
        Double withdrawAmount = 2000.0; // More than balance

        when(accountRepository.findById(1L)).thenReturn(Optional.of(testAccount));

        assertThrows(ValidationException.class, () -> {
            accountService.withdraw(1L, withdrawAmount);
        });

        verify(accountRepository, never()).save(any(Account.class));
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void withdraw_AccountNotFound() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            accountService.withdraw(1L, 500.0);
        });

        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void withdraw_InvalidAmount() {
        assertThrows(ValidationException.class, () -> {
            accountService.withdraw(1L, 0.0);
        });

        verify(accountRepository, never()).save(any(Account.class));
        verify(transactionRepository, never()).save(any(Transaction.class));
    }
}
package com.banking.services;

import com.banking.entities.Account;
import com.banking.entities.Transaction;
import com.banking.repositories.AccountRepository;
import com.banking.repositories.CustomerRepository;
import com.banking.repositories.TransactionRepository;
import com.banking.utils.constants.MessageConstants;
import com.banking.utils.exception.NotFoundException;
import com.banking.utils.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.banking.utils.constants.MessageConstants.*;
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

        // Change assertion to expect NotFoundException instead of IllegalArgumentException
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            accountService.createAccount(1L, "S");
        });

        assertEquals(String.format(MessageConstants.CUSTOMER_NOT_FOUND, 1L), exception.getMessage());
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

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            accountService.deposit(1L, 500.0);
        });

        assertEquals(String.format(ACCOUNT_NOT_FOUND, 1L), exception.getMessage());
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void deposit_NegativeAmount_ConvertedToPositive() {
        Double initialBalance = 1000.0;
        Double depositAmount = -500.0;

        when(accountRepository.findById(1L)).thenReturn(Optional.of(testAccount));
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> {
            Account savedAccount = invocation.getArgument(0);
            testAccount.setBalance(savedAccount.getBalance());
            return testAccount;
        });

        Account result = accountService.deposit(1L, depositAmount);

        assertNotNull(result);
        assertEquals(initialBalance + Math.abs(depositAmount), result.getBalance());
        verify(accountRepository).save(any(Account.class));
        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    void deposit_AmountTooHigh_ThrowsException() {
        Double depositAmount = 1000000.0;

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            accountService.deposit(1L, depositAmount);
        });

        assertEquals(MessageConstants.INVALID_TRANSACTION_AMOUNT, exception.getMessage());
        verify(accountRepository, never()).save(any(Account.class));
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void deposit_NullAmount_ThrowsException() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            accountService.deposit(1L, null);
        });

        assertEquals(INVALID_TRANSACTION_AMOUNT, exception.getMessage());
        verify(accountRepository, never()).save(any(Account.class));
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void withdraw_NegativeAmount_ConvertedToPositive() {
        Double initialBalance = 1000.0;
        Double withdrawAmount = -500.0;

        when(accountRepository.findById(1L)).thenReturn(Optional.of(testAccount));
        when(accountRepository.save(any(Account.class))).thenAnswer(invocation -> {
            Account savedAccount = invocation.getArgument(0);
            testAccount.setBalance(savedAccount.getBalance());
            return testAccount;
        });

        Account result = accountService.withdraw(1L, withdrawAmount);

        assertNotNull(result);
        assertEquals(initialBalance - Math.abs(withdrawAmount), result.getBalance());
        verify(accountRepository).save(any(Account.class));
        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    void withdraw_AmountTooHigh_ThrowsException() {
        Double withdrawAmount = 1000000.0;

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            accountService.withdraw(1L, withdrawAmount);
        });

        assertEquals(MessageConstants.INVALID_TRANSACTION_AMOUNT, exception.getMessage());
        verify(accountRepository, never()).save(any(Account.class));
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void withdraw_NullAmount_ThrowsException() {
        ValidationException exception = assertThrows(ValidationException.class, () -> {
            accountService.withdraw(1L, null);
        });

        assertEquals(INVALID_TRANSACTION_AMOUNT, exception.getMessage());
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

        ValidationException exception = assertThrows(ValidationException.class, () -> {
            accountService.withdraw(1L, withdrawAmount);
        });

        assertEquals(INSUFFICIENT_BALANCE, exception.getMessage());
        verify(accountRepository, never()).save(any(Account.class));
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void withdraw_AccountNotFound() {
        when(accountRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            accountService.withdraw(1L, 500.0);
        });

        assertEquals(String.format(ACCOUNT_NOT_FOUND, 1L), exception.getMessage());
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void getAccountsByCustomerId_Success() {
        when(customerRepository.existsById(1L)).thenReturn(true);
        when(accountRepository.findByCustomerId(1L)).thenReturn(Arrays.asList(testAccount));

        List<Account> result = accountService.getAccountsByCustomerId(1L);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(testAccount.getId(), result.get(0).getId());
    }

    @Test
    void getAccountsByCustomerId_CustomerNotFound() {
        when(customerRepository.existsById(1L)).thenReturn(false);

        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            accountService.getAccountsByCustomerId(1L);
        });

        assertEquals(String.format(CUSTOMER_NOT_FOUND, 1L), exception.getMessage());
        verify(accountRepository, never()).findByCustomerId(any());
    }
}

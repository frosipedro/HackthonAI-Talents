package com.banking.services;

import com.banking.dto.TransactionReportDTO;
import com.banking.entities.Account;
import com.banking.entities.Transaction;
import com.banking.repositories.AccountRepository;
import com.banking.repositories.TransactionRepository;
import com.banking.utils.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransactionService transactionService;

    private Account testAccount;
    private Transaction testTransaction;
    private LocalDate startDate;
    private LocalDate endDate;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testAccount = new Account();
        testAccount.setId(1L);
        testAccount.setCustomerId(1L);
        testAccount.setAccountType("S");
        testAccount.setBalance(1000.0);

        testTransaction = new Transaction();
        testTransaction.setId(1L);
        testTransaction.setAccount(testAccount);
        testTransaction.setType("D");
        testTransaction.setAmount(500.0);
        testTransaction.setDate(LocalDateTime.now());

        startDate = LocalDate.now().minusDays(7);
        endDate = LocalDate.now();
    }

    @Test
    void getTransactionsForCustomer_Success() {
        when(accountRepository.findAll()).thenReturn(Arrays.asList(testAccount));
        when(transactionRepository.findAll()).thenReturn(Arrays.asList(testTransaction));

        List<TransactionReportDTO> result = transactionService.getTransactionsForCustomer(1L, startDate, endDate);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());

        TransactionReportDTO reportDTO = result.get(0);
        assertEquals(testTransaction.getDate().toLocalDate().toString(), reportDTO.getDate());
        assertEquals(testAccount.getAccountType(), reportDTO.getAccountType());
        assertEquals(testTransaction.getType(), reportDTO.getTransactionType());
        assertEquals(testTransaction.getAmount(), reportDTO.getAmount());
    }

    @Test
    void getTransactionsForCustomer_NoAccounts() {
        when(accountRepository.findAll()).thenReturn(Collections.emptyList());

        assertThrows(NotFoundException.class, () -> {
            transactionService.getTransactionsForCustomer(1L, startDate, endDate);
        });
    }

    @Test
    void getTransactionsForCustomer_NoTransactions() {
        when(accountRepository.findAll()).thenReturn(Arrays.asList(testAccount));
        when(transactionRepository.findAll()).thenReturn(Collections.emptyList());

        List<TransactionReportDTO> result = transactionService.getTransactionsForCustomer(1L, startDate, endDate);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getTransactionsForCustomer_FiltersByDateRange() {
        // Create a transaction outside the date range
        Transaction oldTransaction = new Transaction();
        oldTransaction.setAccount(testAccount);
        oldTransaction.setType("W");
        oldTransaction.setAmount(200.0);
        oldTransaction.setDate(LocalDateTime.now().minusDays(10));

        when(accountRepository.findAll()).thenReturn(Arrays.asList(testAccount));
        when(transactionRepository.findAll()).thenReturn(Arrays.asList(testTransaction, oldTransaction));

        List<TransactionReportDTO> result = transactionService.getTransactionsForCustomer(1L, startDate, endDate);

        assertNotNull(result);
        assertEquals(1, result.size()); // Only the transaction within date range should be returned
    }
}
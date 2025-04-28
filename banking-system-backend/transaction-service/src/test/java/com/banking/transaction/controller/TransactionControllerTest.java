package com.banking.transaction.controller;

import com.banking.transaction.model.Transaction;
import com.banking.transaction.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionControllerTest {

    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTransactions() {
        when(transactionService.getAllTransactions()).thenReturn(Collections.emptyList());

        var response = transactionController.getAllTransactions();

        verify(transactionService, times(1)).getAllTransactions();
        assert response != null;
    }

    @Test
    void testGetTransactionById() {
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        when(transactionService.getTransactionById(1L)).thenReturn(Optional.of(transaction));

        var response = transactionController.getTransactionById(1L);

        verify(transactionService, times(1)).getTransactionById(1L);
        assert response.isPresent();
    }

    @Test
    void testCreateTransaction() {
        Transaction transaction = new Transaction();
        when(transactionService.createTransaction(any(Transaction.class))).thenReturn(transaction);

        var response = transactionController.createTransaction(transaction);

        verify(transactionService, times(1)).createTransaction(any(Transaction.class));
        assert response != null;
    }

    @Test
    void testUpdateTransaction() {
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        when(transactionService.updateTransaction(any(Long.class), any(Transaction.class))).thenReturn(Optional.of(transaction));

        var response = transactionController.updateTransaction(1L, transaction);

        verify(transactionService, times(1)).updateTransaction(any(Long.class), any(Transaction.class));
        assert response.isPresent();
    }

    @Test
    void testDeleteTransaction() {
        doNothing().when(transactionService).deleteTransaction(1L);

        transactionController.deleteTransaction(1L);

        verify(transactionService, times(1)).deleteTransaction(1L);
    }
}
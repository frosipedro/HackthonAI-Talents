package com.banking.transaction.service;

import com.banking.transaction.model.Transaction;
import com.banking.transaction.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    private Transaction transaction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAmount(100.0);
        transaction.setDescription("Test Transaction");
    }

    @Test
    void testCreateTransaction() {
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction createdTransaction = transactionService.createTransaction(transaction);

        assertNotNull(createdTransaction);
        assertEquals(transaction.getId(), createdTransaction.getId());
        assertEquals(transaction.getAmount(), createdTransaction.getAmount());
        assertEquals(transaction.getDescription(), createdTransaction.getDescription());
        verify(transactionRepository, times(1)).save(transaction);
    }

    @Test
    void testGetTransactionById() {
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));

        Transaction foundTransaction = transactionService.getTransactionById(1L);

        assertNotNull(foundTransaction);
        assertEquals(transaction.getId(), foundTransaction.getId());
        verify(transactionRepository, times(1)).findById(1L);
    }

    @Test
    void testGetTransactionById_NotFound() {
        when(transactionRepository.findById(1L)).thenReturn(Optional.empty());

        Transaction foundTransaction = transactionService.getTransactionById(1L);

        assertNull(foundTransaction);
        verify(transactionRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateTransaction() {
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        transaction.setAmount(200.0);
        Transaction updatedTransaction = transactionService.updateTransaction(1L, transaction);

        assertNotNull(updatedTransaction);
        assertEquals(200.0, updatedTransaction.getAmount());
        verify(transactionRepository, times(1)).findById(1L);
        verify(transactionRepository, times(1)).save(transaction);
    }

    @Test
    void testDeleteTransaction() {
        doNothing().when(transactionRepository).deleteById(1L);

        transactionService.deleteTransaction(1L);

        verify(transactionRepository, times(1)).deleteById(1L);
    }
}
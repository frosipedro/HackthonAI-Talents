package com.banking.controllers;

import com.banking.dto.TransactionReportDTO;
import com.banking.services.TransactionService;
import com.banking.utils.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    private TransactionReportDTO testReport;
    private LocalDate startDate;
    private LocalDate endDate;

    @BeforeEach
    void setUp() {
        testReport = new TransactionReportDTO(
                LocalDate.now().toString(),
                "S",
                "D",
                500.0);

        startDate = LocalDate.now().minusDays(7);
        endDate = LocalDate.now();
    }

    @Test
    void getTransactionsForCustomer_Success() throws Exception {
        when(transactionService.getTransactionsForCustomer(
                eq(1L),
                any(LocalDate.class),
                any(LocalDate.class)))
                .thenReturn(Arrays.asList(testReport));

        mockMvc.perform(get("/api/transactions/customer/1")
                .param("startDate", startDate.toString())
                .param("endDate", endDate.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].date").value(testReport.getDate()))
                .andExpect(jsonPath("$[0].accountType").value(testReport.getAccountType()))
                .andExpect(jsonPath("$[0].transactionType").value(testReport.getTransactionType()))
                .andExpect(jsonPath("$[0].amount").value(testReport.getAmount()));
    }

    @Test
    void getTransactionsForCustomer_NoTransactions() throws Exception {
        when(transactionService.getTransactionsForCustomer(
                eq(1L),
                any(LocalDate.class),
                any(LocalDate.class)))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/transactions/customer/1")
                .param("startDate", startDate.toString())
                .param("endDate", endDate.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getTransactionsForCustomer_CustomerNotFound() throws Exception {
        when(transactionService.getTransactionsForCustomer(
                eq(1L),
                any(LocalDate.class),
                any(LocalDate.class)))
                .thenThrow(new NotFoundException("Customer not found"));

        mockMvc.perform(get("/api/transactions/customer/1")
                .param("startDate", startDate.toString())
                .param("endDate", endDate.toString()))
                .andExpect(status().isNotFound());
    }

    @Test
    void getTransactionsForCustomer_InvalidDateRange() throws Exception {
        LocalDate invalidEndDate = startDate.minusDays(1); // End date before start date

        mockMvc.perform(get("/api/transactions/customer/1")
                .param("startDate", startDate.toString())
                .param("endDate", invalidEndDate.toString()))
                .andExpect(status().isBadRequest());
    }
}
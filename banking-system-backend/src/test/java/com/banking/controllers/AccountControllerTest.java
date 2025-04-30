package com.banking.controllers;

import com.banking.dto.CreateAccountRequest;
import com.banking.dto.DepositRequest;
import com.banking.dto.WithdrawRequest;
import com.banking.entities.Account;
import com.banking.services.AccountService;
import com.banking.utils.exception.NotFoundException;
import com.banking.utils.exception.ValidationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static com.banking.utils.constants.MessageConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;

    @Autowired
    private ObjectMapper objectMapper;

    private Account testAccount;
    private CreateAccountRequest createAccountRequest;
    private DepositRequest depositRequest;
    private WithdrawRequest withdrawRequest;

    @BeforeEach
    void setUp() {
        testAccount = new Account();
        testAccount.setId(1L);
        testAccount.setCustomerId(1L);
        testAccount.setAccountType("S");
        testAccount.setBalance(1000.0);

        createAccountRequest = new CreateAccountRequest();
        createAccountRequest.setCustomerId(1L);
        createAccountRequest.setAccountType("S");

        depositRequest = new DepositRequest();
        depositRequest.setAccountId(1L);
        depositRequest.setAmount(500.0);

        withdrawRequest = new WithdrawRequest();
        withdrawRequest.setAccountId(1L);
        withdrawRequest.setAmount(300.0);
    }

    @Test
    void createAccount_Success() throws Exception {
        when(accountService.createAccount(eq(1L), eq("S"))).thenReturn(testAccount);

        mockMvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createAccountRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testAccount.getId()))
                .andExpect(jsonPath("$.customerId").value(testAccount.getCustomerId()))
                .andExpect(jsonPath("$.accountType").value(testAccount.getAccountType()));
    }

    @Test
    void createAccount_InvalidRequest() throws Exception {
        CreateAccountRequest invalidRequest = new CreateAccountRequest();
        // Missing required fields

        mockMvc.perform(post("/api/accounts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deposit_Success() throws Exception {
        when(accountService.deposit(eq(1L), eq(500.0))).thenReturn(testAccount);

        mockMvc.perform(post("/api/accounts/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(depositRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testAccount.getId()))
                .andExpect(jsonPath("$.balance").value(testAccount.getBalance()));
    }

    @Test
    void deposit_InvalidAmount() throws Exception {
        when(accountService.deposit(any(), any()))
                .thenThrow(new ValidationException(INVALID_AMOUNT));

        mockMvc.perform(post("/api/accounts/deposit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(depositRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void withdraw_Success() throws Exception {
        when(accountService.withdraw(eq(1L), eq(300.0))).thenReturn(testAccount);

        mockMvc.perform(post("/api/accounts/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(withdrawRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testAccount.getId()))
                .andExpect(jsonPath("$.balance").value(testAccount.getBalance()));
    }

    @Test
    void withdraw_InsufficientBalance() throws Exception {
        when(accountService.withdraw(any(), any()))
                .thenThrow(new ValidationException(INSUFFICIENT_BALANCE));

        mockMvc.perform(post("/api/accounts/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(withdrawRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAccountsByCustomerId_Success() throws Exception {
        List<Account> accounts = Arrays.asList(testAccount);
        when(accountService.getAccountsByCustomerId(1L)).thenReturn(accounts);

        mockMvc.perform(get("/api/accounts/customer/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(testAccount.getId()))
                .andExpect(jsonPath("$[0].customerId").value(testAccount.getCustomerId()))
                .andExpect(jsonPath("$[0].accountType").value(testAccount.getAccountType()));
    }

    @Test
    void getAccountsByCustomerId_CustomerNotFound() throws Exception {
        when(accountService.getAccountsByCustomerId(1L))
                .thenThrow(new NotFoundException(CUSTOMER_NOT_FOUND));

        mockMvc.perform(get("/api/accounts/customer/1"))
                .andExpect(status().isNotFound());
    }
}

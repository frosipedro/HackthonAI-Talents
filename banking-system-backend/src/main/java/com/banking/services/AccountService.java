package com.banking.services;

import com.banking.entities.Account;
import com.banking.entities.Transaction;
import com.banking.repositories.AccountRepository;
import com.banking.repositories.CustomerRepository;
import com.banking.repositories.TransactionRepository;
import com.banking.utils.exception.NotFoundException;
import com.banking.utils.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository, CustomerRepository customerRepository,
            TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
    }

    public Account createAccount(Long customerId, String accountType) {
        // Verificar se o cliente existe
        if (!customerRepository.existsById(customerId)) {
            throw new IllegalArgumentException("Customer with ID " + customerId + " does not exist.");
        }

        // Criar nova conta
        Account account = new Account();
        account.setCustomerId(customerId);
        account.setAccountType(accountType);
        account.setBalance(0.0);

        return accountRepository.save(account);
    }

    public Account deposit(Long accountId, Double amount) {
        if (amount == null) {
            throw new ValidationException("Amount cannot be null");
        }

        // Convert negative to positive before validation
        if (amount < 0) {
            amount = Math.abs(amount);
        }

        validateTransactionAmount(amount);

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account with ID " + accountId + " not found."));

        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setType("D");
        transaction.setAmount(amount);
        transaction.setDate(LocalDateTime.now());
        transactionRepository.save(transaction);

        return account;
    }

    public Account withdraw(Long accountId, Double amount) {
        if (amount == null) {
            throw new ValidationException("Amount cannot be null");
        }

        // Convert negative to positive before validation
        if (amount < 0) {
            amount = Math.abs(amount);
        }

        validateTransactionAmount(amount);

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account with ID " + accountId + " not found."));

        if (account.getBalance() < amount) {
            throw new ValidationException("Insufficient balance for withdrawal.");
        }

        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setType("W");
        transaction.setAmount(amount);
        transaction.setDate(LocalDateTime.now());
        transactionRepository.save(transaction);

        return account;
    }

    private void validateTransactionAmount(Double amount) {
        if (amount <= 0.01 || amount > 999999.99) {
            throw new ValidationException("Transaction amount must be between 0.01 and 999999.99");
        }
    }

    public List<Account> getAccountsByCustomerId(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new NotFoundException("Customer not found with id " + customerId);
        }
        return accountRepository.findByCustomerId(customerId);
    }
}
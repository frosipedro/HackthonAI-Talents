package com.banking.services;

import com.banking.entities.Account;
import com.banking.repositories.AccountRepository;
import com.banking.repositories.CustomerRepository;
import com.banking.utils.exception.NotFoundException;
import com.banking.utils.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository, CustomerRepository customerRepository) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
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
        validateTransactionAmount(amount);

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account with ID " + accountId + " not found."));

        account.setBalance(account.getBalance() + amount);
        return accountRepository.save(account);
    }

    public Account withdraw(Long accountId, Double amount) {
        validateTransactionAmount(amount);

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException("Account with ID " + accountId + " not found."));

        if (account.getBalance() < amount) {
            throw new ValidationException("Insufficient balance for withdrawal.");
        }

        account.setBalance(account.getBalance() - amount);
        return accountRepository.save(account);
    }

    private void validateTransactionAmount(Double amount) {
        if (amount == null || amount <= 0.01 || amount > 999999.99) {
            throw new ValidationException("Transaction amount must be between 0.01 and 999999.99.");
        }
    }
}
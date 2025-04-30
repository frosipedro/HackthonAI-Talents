package com.banking.services;

import com.banking.entities.Account;
import com.banking.entities.Transaction;
import com.banking.repositories.AccountRepository;
import com.banking.repositories.CustomerRepository;
import com.banking.repositories.TransactionRepository;
import com.banking.utils.constants.MessageConstants;
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
        if (!customerRepository.existsById(customerId)) {
            throw new NotFoundException(String.format(MessageConstants.CUSTOMER_NOT_FOUND, customerId));
        }

        Account account = new Account();
        account.setCustomerId(customerId);
        account.setAccountType(accountType);
        account.setBalance(0.0);

        return accountRepository.save(account);
    }

    public Account deposit(Long accountId, Double amount) {
        if (amount == null) {
            throw new ValidationException(MessageConstants.INVALID_TRANSACTION_AMOUNT);
        }

        if (amount < 0) {
            amount = Math.abs(amount);
        }

        validateTransactionAmount(amount);

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException(String.format(MessageConstants.ACCOUNT_NOT_FOUND, accountId)));

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
            throw new ValidationException(MessageConstants.INVALID_TRANSACTION_AMOUNT);
        }

        if (amount < 0) {
            amount = Math.abs(amount);
        }

        validateTransactionAmount(amount);

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new NotFoundException(String.format(MessageConstants.ACCOUNT_NOT_FOUND, accountId)));

        if (account.getBalance() < amount) {
            throw new ValidationException(MessageConstants.INSUFFICIENT_BALANCE);
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
            throw new ValidationException(MessageConstants.INVALID_TRANSACTION_AMOUNT);
        }
    }

    public List<Account> getAccountsByCustomerId(Long customerId) {
        if (!customerRepository.existsById(customerId)) {
            throw new NotFoundException(String.format(MessageConstants.CUSTOMER_NOT_FOUND, customerId));
        }
        return accountRepository.findByCustomerId(customerId);
    }
}

package com.banking.services;

import com.banking.dto.TransactionReportDTO;
import com.banking.entities.Account;
import com.banking.entities.Transaction;
import com.banking.repositories.AccountRepository;
import com.banking.repositories.TransactionRepository;
import com.banking.utils.constants.MessageConstants;
import com.banking.utils.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountRepository = accountRepository;
    }

    public List<TransactionReportDTO> getTransactionsForCustomer(Long customerId, LocalDate startDate,
                                                                 LocalDate endDate) {
        List<Account> accounts = accountRepository.findByCustomerId(customerId);

        if (accounts.isEmpty()) {
            throw new NotFoundException(String.format(MessageConstants.CUSTOMER_NOT_FOUND, customerId));
        }

        List<Transaction> transactions = transactionRepository.findAll()
                .stream()
                .filter(transaction -> accounts.contains(transaction.getAccount()) &&
                        !transaction.getDate().toLocalDate().isBefore(startDate) &&
                        !transaction.getDate().toLocalDate().isAfter(endDate))
                .collect(Collectors.toList());

        return transactions.stream()
                .map(transaction -> new TransactionReportDTO(
                        transaction.getDate().toLocalDate().toString(),
                        transaction.getAccount().getAccountType(),
                        transaction.getType(),
                        transaction.getAmount()))
                .collect(Collectors.toList());
    }
}

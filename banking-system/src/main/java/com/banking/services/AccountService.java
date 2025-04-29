package com.banking.services;

import com.banking.entities.Account;
import com.banking.entities.Customer;
import com.banking.repositories.AccountRepository;
import com.banking.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

        // Verificar se o cliente já possui uma conta do tipo especificado
        if (accountRepository.findByCustomerIdAndAccountType(customerId, accountType).isPresent()) {
            throw new IllegalArgumentException(
                    "Customer with ID " + customerId + " already has an account of type " + accountType + ".");
        }

        // Criar nova conta
        Account account = new Account();
        account.setCustomerId(customerId);
        account.setAccountType(accountType);
        account.setBalance(0.0);

        return accountRepository.save(account);
    }
}
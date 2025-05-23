package com.banking.services;

import com.banking.entities.Customer;
import com.banking.entities.Account;
import com.banking.repositories.CustomerRepository;
import com.banking.repositories.AccountRepository;
import com.banking.repositories.TransactionRepository;
import com.banking.utils.ValidationUtils;
import com.banking.utils.constants.MessageConstants;
import com.banking.utils.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository; // Adicionar isso

    @Autowired
    public CustomerService(
            CustomerRepository customerRepository,
            AccountRepository accountRepository,
            TransactionRepository transactionRepository) { // Adicionar parâmetro
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository; // Adicionar isso
    }

        public Customer createCustomer(Customer customer) {
            if (!ValidationUtils.isValidEmail(customer.getEmail())) {
                throw new ValidationException(MessageConstants.INVALID_EMAIL_FORMAT);
            }

            if (!ValidationUtils.isValidName(customer.getName())) {
                throw new ValidationException(MessageConstants.NAME_REQUIRED);
            }

            Optional<Customer> existingCustomer = customerRepository.findByEmail(customer.getEmail());
            if (existingCustomer.isPresent()) {
                throw new ValidationException(MessageConstants.EMAIL_ALREADY_EXISTS);
            }

            if (!ValidationUtils.isNotFutureDate(customer.getBirthDate())) {
                throw new ValidationException(MessageConstants.FUTURE_DATE_NOT_ALLOWED);
            }

            if (!ValidationUtils.isAdult(customer.getBirthDate())) {
                throw new ValidationException(MessageConstants.MINIMUM_AGE_REQUIRED);
            }

            return customerRepository.save(customer);
        }

        public Customer updateCustomer(Long id, Customer customerDetails) {
            Optional<Customer> customer = customerRepository.findById(id);
            if (customer.isEmpty()) {
                throw new ValidationException(String.format(MessageConstants.CUSTOMER_NOT_FOUND, id));
            }

            Customer existingCustomer = customer.get();

            if (!ValidationUtils.isValidName(customerDetails.getName())) {
                throw new ValidationException(MessageConstants.NAME_REQUIRED);
            }

            if (!ValidationUtils.isValidEmail(customerDetails.getEmail())) {
                throw new ValidationException(MessageConstants.INVALID_EMAIL_FORMAT);
            }

            Optional<Customer> customerWithEmail = customerRepository.findByEmail(customerDetails.getEmail());
            if (customerWithEmail.isPresent() && !customerWithEmail.get().getId().equals(id)) {
                throw new ValidationException(MessageConstants.EMAIL_ALREADY_EXISTS);
            }

            if (customerDetails.getBirthDate() != null) {
                if (!ValidationUtils.isValidDate(customerDetails.getBirthDate())) {
                    throw new ValidationException(MessageConstants.INVALID_DATE_FORMAT);
                }
                if (!ValidationUtils.isNotFutureDate(customerDetails.getBirthDate())) {
                    throw new ValidationException(MessageConstants.FUTURE_DATE_NOT_ALLOWED);
                }
                if (!ValidationUtils.isAdult(customerDetails.getBirthDate())) {
                    throw new ValidationException(MessageConstants.MINIMUM_AGE_REQUIRED);
                }
            }

            existingCustomer.setName(customerDetails.getName());
            existingCustomer.setEmail(customerDetails.getEmail());
            existingCustomer.setBirthDate(customerDetails.getBirthDate());

            return customerRepository.save(existingCustomer);
        }

        public Customer updateCustomerPartial(Long id, Customer customerDetails) {
            Customer existingCustomer = customerRepository.findById(id)
                    .orElseThrow(() -> new ValidationException(String.format(MessageConstants.CUSTOMER_NOT_FOUND, id)));

            if (customerDetails.getName() != null) {
                if (!ValidationUtils.isValidName(customerDetails.getName())) {
                    throw new ValidationException(MessageConstants.NAME_REQUIRED);
                }
                existingCustomer.setName(customerDetails.getName());
            }

            if (customerDetails.getEmail() != null) {
                if (!ValidationUtils.isValidEmail(customerDetails.getEmail())) {
                    throw new ValidationException(MessageConstants.INVALID_EMAIL_FORMAT);
                }
                Optional<Customer> customerWithEmail = customerRepository.findByEmail(customerDetails.getEmail());
                if (customerWithEmail.isPresent() && !customerWithEmail.get().getId().equals(id)) {
                    throw new ValidationException(MessageConstants.EMAIL_ALREADY_EXISTS);
                }
                existingCustomer.setEmail(customerDetails.getEmail());
            }

            if (customerDetails.getBirthDate() != null) {
                if (!ValidationUtils.isValidDate(customerDetails.getBirthDate())) {
                    throw new ValidationException(MessageConstants.INVALID_DATE_FORMAT);
                }
                if (!ValidationUtils.isNotFutureDate(customerDetails.getBirthDate())) {
                    throw new ValidationException(MessageConstants.FUTURE_DATE_NOT_ALLOWED);
                }
                if (!ValidationUtils.isAdult(customerDetails.getBirthDate())) {
                    throw new ValidationException(MessageConstants.MINIMUM_AGE_REQUIRED);
                }
                existingCustomer.setBirthDate(customerDetails.getBirthDate());
            }

            return customerRepository.save(existingCustomer);
    }

    public Customer getCustomerById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()) {
            throw new ValidationException(String.format(MessageConstants.CUSTOMER_NOT_FOUND, id));
        }
        return customer.get();
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public void deleteCustomer(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()) {
            throw new ValidationException(String.format(MessageConstants.CUSTOMER_NOT_FOUND, id));
        }

        List<Account> customerAccounts = accountRepository.findByCustomerId(id);

        for (Account account : customerAccounts) {
            transactionRepository.deleteByAccountId(account.getId());
        }

        accountRepository.deleteAll(customerAccounts);

        customerRepository.deleteById(id);
    }

}

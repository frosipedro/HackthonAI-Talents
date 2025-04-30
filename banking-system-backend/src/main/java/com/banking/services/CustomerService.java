package com.banking.services;

import com.banking.entities.Customer;
import com.banking.entities.Account;
import com.banking.repositories.CustomerRepository;
import com.banking.repositories.AccountRepository;
import com.banking.utils.ValidationUtils;
import com.banking.utils.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository; // Adiciona AccountRepository

    @Autowired
    public CustomerService(CustomerRepository customerRepository, AccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    public Customer createCustomer(Customer customer) {

        if (!ValidationUtils.isValidEmail(customer.getEmail())) {
            throw new ValidationException("Invalid email format");
        }

        Optional<Customer> existingCustomer = customerRepository.findByEmail(customer.getEmail());
        if (existingCustomer.isPresent()) {
            throw new ValidationException("Email already exists");
        }

        if (!ValidationUtils.isNotFutureDate(customer.getBirthDate())) {
            throw new ValidationException("Birth date cannot be in the future");
        }
        return customerRepository.save(customer);
    }

    public Customer getCustomerById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()) {
            throw new ValidationException("Customer not found with id " + id);
        }
        return customer.get();
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer updateCustomer(Long id, Customer customerDetails) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()) {
            throw new ValidationException("Customer not found with id " + id);
        }
        Customer existingCustomer = customer.get();
        existingCustomer.setName(customerDetails.getName());
        existingCustomer.setEmail(customerDetails.getEmail());
        existingCustomer.setBirthDate(customerDetails.getBirthDate());
        return customerRepository.save(existingCustomer);
    }

    public void deleteCustomer(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()) {
            throw new ValidationException("Customer not found with id " + id);
        }

        List<Account> customerAccounts = accountRepository.findByCustomerId(id);
        accountRepository.deleteAll(customerAccounts);

        customerRepository.deleteById(id);
    }
}

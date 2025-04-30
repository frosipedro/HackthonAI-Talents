package com.banking.services;

import com.banking.entities.Customer;
import com.banking.entities.Account;
import com.banking.repositories.CustomerRepository;
import com.banking.repositories.AccountRepository;
import com.banking.utils.ValidationUtils;
import com.banking.utils.exception.NotFoundException;
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

        if (!ValidationUtils.isValidCPF(customer.getCpf())) {
            throw new ValidationException("Invalid CPF format");
        }

        Optional<Customer> existingCPF = customerRepository.findByCpf(customer.getCpf());
        if (existingCPF.isPresent()) {
            throw new ValidationException("CPF already exists");
        }

        if (!ValidationUtils.isNotFutureDate(customer.getBirthDate())) {
            throw new ValidationException("Birth date cannot be in the future");
        }

        if (!ValidationUtils.isAdult(customer.getBirthDate())) {
            throw new ValidationException("Customer must be at least 18 years old");
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

        if (customerDetails.getCpf() != null && !customerDetails.getCpf().equals(existingCustomer.getCpf())) {
            if (!ValidationUtils.isValidCPF(customerDetails.getCpf())) {
                throw new ValidationException("Invalid CPF format");
            }

            Optional<Customer> existingCPF = customerRepository.findByCpf(customerDetails.getCpf());
            if (existingCPF.isPresent()) {
                throw new ValidationException("CPF already exists");
            }
            existingCustomer.setCpf(customerDetails.getCpf());
        }

        if (customerDetails.getName() != null) {
            existingCustomer.setName(customerDetails.getName());
        }
        if (customerDetails.getEmail() != null) {
            if (!ValidationUtils.isValidEmail(customerDetails.getEmail())) {
                throw new ValidationException("Invalid email format");
            }
            existingCustomer.setEmail(customerDetails.getEmail());
        }
        if (customerDetails.getBirthDate() != null) {
            if (!ValidationUtils.isNotFutureDate(customerDetails.getBirthDate())) {
                throw new ValidationException("Birth date cannot be in the future");
            }
            existingCustomer.setBirthDate(customerDetails.getBirthDate());
        }

        return customerRepository.save(existingCustomer);
    }

    public Customer updateCustomerPartial(Long id, Customer customerDetails) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Customer not found"));

        if (customerDetails.getName() != null) {
            if (customerDetails.getName().trim().isEmpty()) {
                throw new ValidationException("Name cannot be empty");
            }
            existingCustomer.setName(customerDetails.getName());
        }

        if (customerDetails.getEmail() != null) {
            if (!ValidationUtils.isValidEmail(customerDetails.getEmail())) {
                throw new ValidationException("Invalid email format");
            }
            // Verificar se o email já existe para outro cliente
            Optional<Customer> customerWithEmail = customerRepository.findByEmail(customerDetails.getEmail());
            if (customerWithEmail.isPresent() && !customerWithEmail.get().getId().equals(id)) {
                throw new ValidationException("Email already in use by another customer");
            }
            existingCustomer.setEmail(customerDetails.getEmail());
        }

        if (customerDetails.getBirthDate() != null) {
            if (!ValidationUtils.isValidDate(customerDetails.getBirthDate())) {
                throw new ValidationException("Invalid date format. Use YYYY-MM-DD");
            }
            if (!ValidationUtils.isNotFutureDate(customerDetails.getBirthDate())) {
                throw new ValidationException("Birth date cannot be in the future");
            }
            if (!ValidationUtils.isAdult(customerDetails.getBirthDate())) {
                throw new ValidationException("Customer must be at least 18 years old");
            }
            existingCustomer.setBirthDate(customerDetails.getBirthDate());
        }

        if (customerDetails.getCpf() != null) {
            if (!ValidationUtils.isValidCPF(customerDetails.getCpf())) {
                throw new ValidationException("Invalid CPF format");
            }
            // Verificar se o CPF já existe para outro cliente
            Optional<Customer> customerWithCPF = customerRepository.findByCpf(customerDetails.getCpf());
            if (customerWithCPF.isPresent() && !customerWithCPF.get().getId().equals(id)) {
                throw new ValidationException("CPF already in use by another customer");
            }
            existingCustomer.setCpf(customerDetails.getCpf());
        }

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

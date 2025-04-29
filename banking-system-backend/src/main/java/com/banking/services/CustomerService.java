package com.banking.services;

import com.banking.entities.Customer;
import com.banking.repositories.CustomerRepository;
import com.banking.utils.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(Customer customer) {
        Optional<Customer> existingCustomer = customerRepository.findByEmail(customer.getEmail());
        if (existingCustomer.isPresent()) {
            throw new ValidationException("Email is already taken");
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
        customerRepository.deleteById(id);
    }    
}
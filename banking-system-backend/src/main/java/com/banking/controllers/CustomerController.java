package com.banking.controllers;

import com.banking.entities.Customer;
import com.banking.services.CustomerService;
import com.banking.utils.exception.NotFoundException;
import com.banking.utils.exception.ValidationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
@Validated
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<?> createCustomer(@RequestBody Customer customer) {
        try {
            if (customer.getEmail() == null || customer.getName() == null || customer.getBirthDate() == null) {
                return ResponseEntity.badRequest()
                        .body(Collections.singletonMap("error", "Missing required fields"));
            }
            Customer createdCustomer = customerService.createCustomer(customer);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCustomerPartial(@PathVariable Long id, @RequestBody Customer customerDetails) {
        try {
            Customer updatedCustomer = customerService.updateCustomerPartial(id, customerDetails);
            return ResponseEntity.ok(updatedCustomer);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("error", e.getMessage()));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id);
        return ResponseEntity.ok().body(customer);
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return ResponseEntity.ok().body(customers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id,
            @Valid @RequestBody Customer customerDetails) {
        Customer updatedCustomer = customerService.updateCustomer(id, customerDetails);
        return ResponseEntity.ok().body(updatedCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
package com.banking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.banking.entities.Customer;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
}
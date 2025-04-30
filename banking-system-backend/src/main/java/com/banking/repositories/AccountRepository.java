package com.banking.repositories;

import com.banking.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByCustomerIdAndAccountType(Long customerId, String accountType);
    List<Account> findByCustomerId(Long customerId);
}
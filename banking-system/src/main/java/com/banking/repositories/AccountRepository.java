package com.banking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.banking.entities.Account;
import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByCustomerId(Long customerId);

    Optional<Account> findById(Long id);
}
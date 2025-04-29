package com.banking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.banking.entities.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountId(Long accountId);
}
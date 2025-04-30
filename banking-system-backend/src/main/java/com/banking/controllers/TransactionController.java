package com.banking.controllers;

import com.banking.dto.TransactionReportDTO;
import com.banking.services.TransactionService;
import com.banking.utils.constants.MessageConstants;
import com.banking.utils.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<TransactionReportDTO>> getTransactionsForCustomer(
            @PathVariable Long customerId,
            @RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

        // Validação das datas
        if (startDate == null || endDate == null) {
            throw new ValidationException("Start date and end date are required");
        }

        if (startDate.isAfter(endDate)) {
            throw new ValidationException(MessageConstants.INVALID_DATE_RANGE);
        }

        List<TransactionReportDTO> transactions = transactionService.getTransactionsForCustomer(
                customerId,
                startDate,
                endDate
        );

        return ResponseEntity.ok(transactions);
    }
}

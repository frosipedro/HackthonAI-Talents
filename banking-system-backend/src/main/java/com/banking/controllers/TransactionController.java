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

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<TransactionReportDTO>> getTransactionsForCustomer(
            @PathVariable Long customerId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        if (startDate.isAfter(endDate)) {
            throw new ValidationException(MessageConstants.INVALID_DATE_RANGE);
        }

        List<TransactionReportDTO> transactions = transactionService.getTransactionsForCustomer(customerId, startDate,
                endDate);
        return ResponseEntity.ok(transactions);
    }
}

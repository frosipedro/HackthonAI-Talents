package com.banking.utils;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ValidationUtils {

    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+\\.[A-Za-z0-9-.]+$";
        return email.matches(emailRegex);
    }

    public static boolean isValidDate(String date) {
        String dateRegex = "^(19|20)\\d\\d-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";
        return date != null && date.matches(dateRegex);
    }

    public static boolean isNotFutureDate(String date) {
        try {
            LocalDate inputDate = LocalDate.parse(date);
            LocalDate currentDate = LocalDate.now();
            return !inputDate.isAfter(currentDate);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean isValidAmount(double amount) {
        return amount > 0;
    }

    public static boolean isNotEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }
}
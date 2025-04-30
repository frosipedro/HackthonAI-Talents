package com.banking.utils;

import com.banking.utils.exception.ValidationException;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeParseException;

public class ValidationUtils {

    private static final int MAX_NAME_LENGTH = 30;
    private static final int MAX_EMAIL_LENGTH = 50;

    public static boolean isValidName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("Nome é obrigatório");
        }
        if (name.length() > MAX_NAME_LENGTH) {
            throw new ValidationException("Nome não pode exceder " + MAX_NAME_LENGTH + " caracteres");
        }
        return true;
    }

    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new ValidationException("Email é obrigatório");
        }
        if (email.length() > MAX_EMAIL_LENGTH) {
            throw new ValidationException("Email não pode exceder " + MAX_EMAIL_LENGTH + " caracteres");
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9-]+\\.[A-Za-z0-9-.]+$";
        if (!email.matches(emailRegex)) {
            throw new ValidationException("Invalid email format"); // Changed to match test expectation
        }

        return true;
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

    public static boolean isAdult(String birthDate) {
        try {
            LocalDate dob = LocalDate.parse(birthDate);
            LocalDate now = LocalDate.now();
            return Period.between(dob, now).getYears() >= 18;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean isValidCPF(String cpf) {
        if (cpf == null || !cpf.matches("^\\d{11}$")) {
            return false;
        }

        // Check if all digits are the same
        if (cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        // Calculate first digit
        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int firstDigit = 11 - (sum % 11);
        if (firstDigit >= 10) firstDigit = 0;

        // Calculate second digit
        sum = 0;
        for (int i = 0; i < 10; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        int secondDigit = 11 - (sum % 11);
        if (secondDigit >= 10) secondDigit = 0;

        return Character.getNumericValue(cpf.charAt(9)) == firstDigit &&
                Character.getNumericValue(cpf.charAt(10)) == secondDigit;
    }

    public static boolean isValidAmount(double amount) {
        return amount > 0;
    }

    public static boolean isNotEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }
}
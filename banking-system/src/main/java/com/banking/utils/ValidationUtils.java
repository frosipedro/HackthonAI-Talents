package com.banking.utils;

public class ValidationUtils {

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email != null && email.matches(emailRegex);
    }

    public static boolean isValidDate(String date) {
        String dateRegex = "^(19|20)\\d\\d-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";
        return date != null && date.matches(dateRegex);
    }

    public static boolean isValidAmount(double amount) {
        return amount > 0;
    }

    public static boolean isNotEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }
}
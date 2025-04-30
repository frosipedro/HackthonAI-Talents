package com.banking.utils.constants;

public class MessageConstants {
    // General validation messages
    public static final String REQUIRED_FIELD = "Field %s is required";
    public static final String FIELD_MAX_LENGTH = "Field %s cannot exceed %d characters";

    // Customer validation messages
    public static final String NAME_REQUIRED = "Name is required";
    public static final String EMAIL_REQUIRED = "Email is required";
    public static final String INVALID_EMAIL_FORMAT = "Invalid email format";
    public static final String EMAIL_ALREADY_EXISTS = "Email is already in use by another customer";
    public static final String INVALID_DATE_FORMAT = "Invalid date format. Use YYYY-MM-DD";
    public static final String FUTURE_DATE_NOT_ALLOWED = "Birth date cannot be in the future";
    public static final String MINIMUM_AGE_REQUIRED = "Customer must be at least 18 years old";
    public static final String INVALID_CPF_FORMAT = "Invalid CPF format";
    public static final String CPF_ALREADY_EXISTS = "CPF is already registered for another customer";
    public static final String CUSTOMER_NOT_FOUND = "Customer not found with ID: %d";

    // Account validation messages
    public static final String ACCOUNT_NOT_FOUND = "Account not found with ID: %d";
    public static final String NO_ACCOUNTS_FOR_CUSTOMER = "No accounts found for customer ID %d";
    public static final String INVALID_AMOUNT = "Invalid amount";
    public static final String TRANSACTION_AMOUNT_EXCEEDS_LIMIT = "Transaction amount exceeds the limit";
    public static final String INSUFFICIENT_BALANCE = "Insufficient balance for withdrawal";
    public static final String INVALID_ACCOUNT_TYPE = "Account type must be 'C' or 'S'";
    public static final String CUSTOMER_ID_REQUIRED = "Customer ID is required";

    // Transaction validation messages
    public static final String INVALID_TRANSACTION_AMOUNT = "Transaction amount must be greater than zero";
    public static final String INVALID_DATE_RANGE = "Start date must be before or equal to end date";
    public static final String TRANSACTION_NOT_FOUND = "Transaction not found with ID: %d";

    // Server error messages
    public static final String INTERNAL_SERVER_ERROR = "Internal server error: %s";
    public static final String INVALID_REQUEST_PARAMETERS = "Invalid request parameters";
}
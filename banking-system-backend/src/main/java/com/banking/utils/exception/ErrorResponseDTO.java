package com.banking.utils.exception;

import java.time.LocalDateTime;

public class ErrorResponseDTO {
    private String message;
    private String code;
    private LocalDateTime timestamp;

    public ErrorResponseDTO(String message, String code, LocalDateTime timestamp) {
        this.message = message;
        this.code = code;
        this.timestamp = timestamp;
    }

    // Getters e Setters
    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}

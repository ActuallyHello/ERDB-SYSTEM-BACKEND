package com.ustu.erdbsystem.tasks.exception.service;

public class ConvertEntityToJsonException extends RuntimeException {
    public ConvertEntityToJsonException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConvertEntityToJsonException(String message) {
        super(message);
    }
}

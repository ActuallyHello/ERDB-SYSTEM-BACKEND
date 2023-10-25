package com.ustu.erdbsystem.persons.exception.service;

public class StudentCreationException extends RuntimeException {
    public StudentCreationException(String message) {
        super(message);
    }

    public StudentCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}

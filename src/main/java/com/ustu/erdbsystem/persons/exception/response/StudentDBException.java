package com.ustu.erdbsystem.persons.exception.response;

public class StudentDBException extends RuntimeException {
    public StudentDBException(String message) {
        super(message);
    }

    public StudentDBException(String message, Throwable cause) {
        super(message, cause);
    }
}

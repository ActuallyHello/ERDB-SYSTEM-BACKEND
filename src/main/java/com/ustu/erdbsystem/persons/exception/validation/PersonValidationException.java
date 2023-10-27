package com.ustu.erdbsystem.persons.exception.validation;

public class PersonValidationException extends RuntimeException {
    public PersonValidationException(String message) {
        super(message);
    }

    public PersonValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}

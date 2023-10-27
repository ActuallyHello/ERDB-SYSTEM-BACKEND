package com.ustu.erdbsystem.persons.exception.response;

public class PersonDBException extends RuntimeException {
    public PersonDBException(String message) {
        super(message);
    }

    public PersonDBException(String message, Throwable cause) {
        super(message, cause);
    }
}

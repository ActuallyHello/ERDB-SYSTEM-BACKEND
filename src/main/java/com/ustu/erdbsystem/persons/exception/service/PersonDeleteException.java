package com.ustu.erdbsystem.persons.exception.service;

public class PersonDeleteException extends RuntimeException {
    public PersonDeleteException(String message) {
        super(message);
    }

    public PersonDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}

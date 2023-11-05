package com.ustu.erdbsystem.persons.exception.response;

import com.ustu.erdbsystem.persons.exception.NotFoundException;

public class PersonNotFoundException extends RuntimeException implements NotFoundException {
    public PersonNotFoundException(String message) {
        super(message);
    }

    public PersonNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

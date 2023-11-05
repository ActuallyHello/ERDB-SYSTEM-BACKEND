package com.ustu.erdbsystem.persons.exception.response;

import com.ustu.erdbsystem.persons.exception.NotFoundException;

public class UserNotFoundException extends RuntimeException implements NotFoundException {
    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

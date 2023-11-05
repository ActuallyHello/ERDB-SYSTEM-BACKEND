package com.ustu.erdbsystem.persons.exception.response;

import com.ustu.erdbsystem.persons.exception.NotFoundException;

public class PositionNotFoundException extends RuntimeException implements NotFoundException {
    public PositionNotFoundException(String message) {
        super(message);
    }

    public PositionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

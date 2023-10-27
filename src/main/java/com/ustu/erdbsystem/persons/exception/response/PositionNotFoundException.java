package com.ustu.erdbsystem.persons.exception.response;

public class PositionNotFoundException extends RuntimeException {
    public PositionNotFoundException(String message) {
        super(message);
    }

    public PositionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

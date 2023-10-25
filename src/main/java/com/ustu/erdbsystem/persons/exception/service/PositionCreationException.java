package com.ustu.erdbsystem.persons.exception.service;

public class PositionCreationException extends RuntimeException {
    public PositionCreationException(String message) {
        super(message);
    }

    public PositionCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}

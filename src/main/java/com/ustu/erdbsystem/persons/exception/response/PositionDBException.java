package com.ustu.erdbsystem.persons.exception.response;

public class PositionDBException extends RuntimeException {
    public PositionDBException(String message) {
        super(message);
    }

    public PositionDBException(String message, Throwable cause) {
        super(message, cause);
    }
}

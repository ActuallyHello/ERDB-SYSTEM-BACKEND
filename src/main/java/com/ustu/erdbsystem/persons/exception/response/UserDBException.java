package com.ustu.erdbsystem.persons.exception.response;

public class UserDBException extends RuntimeException {
    public UserDBException(String message) {
        super(message);
    }

    public UserDBException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.ustu.erdbsystem.persons.exception.response;

public class GroupDBException extends RuntimeException {
    public GroupDBException(String message) {
        super(message);
    }

    public GroupDBException(String message, Throwable cause) {
        super(message, cause);
    }
}

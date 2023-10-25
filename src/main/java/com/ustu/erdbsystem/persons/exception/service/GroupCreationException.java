package com.ustu.erdbsystem.persons.exception.service;

public class GroupCreationException extends RuntimeException {
    public GroupCreationException(String message) {
        super(message);
    }

    public GroupCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}

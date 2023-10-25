package com.ustu.erdbsystem.ermodels.exception.service;

public class RelationCreationException extends RuntimeException {
    public RelationCreationException(String message) {
        super(message);
    }

    public RelationCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.ustu.erdbsystem.ermodels.exception.service;

public class RelationDeleteException extends RuntimeException {
    public RelationDeleteException(String message) {
        super(message);
    }

    public RelationDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}

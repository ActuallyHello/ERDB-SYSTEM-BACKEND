package com.ustu.erdbsystem.ermodels.exception.service;

public class ModelCreationException extends RuntimeException {
    public ModelCreationException(String message) {
        super(message);
    }

    public ModelCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}

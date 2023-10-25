package com.ustu.erdbsystem.ermodels.exception.service;

public class ModelEntityCreationException extends RuntimeException {
    public ModelEntityCreationException(String message) {
        super(message);
    }

    public ModelEntityCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.ustu.erdbsystem.ermodels.exception.response;

public class ModelValidationException extends RuntimeException {
    public ModelValidationException(String message) {
        super(message);
    }

    public ModelValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}

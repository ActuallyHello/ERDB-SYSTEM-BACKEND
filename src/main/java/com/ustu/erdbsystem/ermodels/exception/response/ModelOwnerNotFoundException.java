package com.ustu.erdbsystem.ermodels.exception.response;

public class ModelOwnerNotFoundException extends RuntimeException {
    public ModelOwnerNotFoundException(String message) {
        super(message);
    }

    public ModelOwnerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

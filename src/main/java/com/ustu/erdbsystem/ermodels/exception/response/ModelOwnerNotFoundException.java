package com.ustu.erdbsystem.ermodels.exception.response;

import com.ustu.erdbsystem.ermodels.exception.NotFoundException;

public class ModelOwnerNotFoundException extends RuntimeException implements NotFoundException {
    public ModelOwnerNotFoundException(String message) {
        super(message);
    }

    public ModelOwnerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

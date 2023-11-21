package com.ustu.erdbsystem.ermodels.exception.response;

import com.ustu.erdbsystem.exceptions.NotFoundException;

public class ModelNotFoundException extends RuntimeException implements NotFoundException {
    public ModelNotFoundException(String message) {
        super(message);
    }

    public ModelNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

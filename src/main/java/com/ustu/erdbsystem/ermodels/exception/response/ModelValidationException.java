package com.ustu.erdbsystem.ermodels.exception.response;

import com.ustu.erdbsystem.ermodels.exception.ServerException;

public class ModelValidationException extends RuntimeException implements ServerException {
    public ModelValidationException(String message) {
        super(message);
    }

    public ModelValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}

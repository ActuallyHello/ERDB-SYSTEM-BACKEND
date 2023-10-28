package com.ustu.erdbsystem.ermodels.exception.response;

import com.ustu.erdbsystem.ermodels.exception.ServerException;

public class ModelServerException extends RuntimeException implements ServerException {

    public ModelServerException(String message) {
        super(message);
    }

    public ModelServerException(String message, Throwable cause) {
        super(message, cause);
    }
}

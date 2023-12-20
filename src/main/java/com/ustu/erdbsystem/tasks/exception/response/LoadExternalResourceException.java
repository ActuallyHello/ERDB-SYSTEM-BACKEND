package com.ustu.erdbsystem.tasks.exception.response;

import com.ustu.erdbsystem.exceptions.ServerException;

public class LoadExternalResourceException extends RuntimeException implements ServerException {
    public LoadExternalResourceException(String message) {
        super(message);
    }

    public LoadExternalResourceException(String message, Throwable cause) {
        super(message, cause);
    }
}

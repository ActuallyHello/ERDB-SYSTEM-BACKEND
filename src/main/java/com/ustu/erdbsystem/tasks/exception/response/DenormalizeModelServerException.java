package com.ustu.erdbsystem.tasks.exception.response;

import com.ustu.erdbsystem.exceptions.ServerException;

public class DenormalizeModelServerException extends RuntimeException implements ServerException {
    public DenormalizeModelServerException(String message) {
        super(message);
    }

    public DenormalizeModelServerException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.ustu.erdbsystem.persons.exception.response;

import com.ustu.erdbsystem.exceptions.ServerException;

public class PositionServerException extends RuntimeException implements ServerException {
    public PositionServerException(String message) {
        super(message);
    }

    public PositionServerException(String message, Throwable cause) {
        super(message, cause);
    }
}

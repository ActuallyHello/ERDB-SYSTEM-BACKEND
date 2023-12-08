package com.ustu.erdbsystem.tasks.exception.response;

import com.ustu.erdbsystem.exceptions.ServerException;

public class ResultServerException extends RuntimeException implements ServerException {
    public ResultServerException(String message) {
        super(message);
    }

    public ResultServerException(String message, Throwable cause) {
        super(message, cause);
    }
}

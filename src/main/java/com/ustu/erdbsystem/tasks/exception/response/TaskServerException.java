package com.ustu.erdbsystem.tasks.exception.response;

import com.ustu.erdbsystem.exceptions.ServerException;

public class TaskServerException extends RuntimeException implements ServerException {
    public TaskServerException(String message) {
        super(message);
    }

    public TaskServerException(String message, Throwable cause) {
        super(message, cause);
    }
}

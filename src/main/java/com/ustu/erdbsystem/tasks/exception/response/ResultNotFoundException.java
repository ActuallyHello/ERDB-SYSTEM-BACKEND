package com.ustu.erdbsystem.tasks.exception.response;

import com.ustu.erdbsystem.exceptions.NotFoundException;

public class ResultNotFoundException extends RuntimeException implements NotFoundException {
    public ResultNotFoundException(String message) {
        super(message);
    }

    public ResultNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

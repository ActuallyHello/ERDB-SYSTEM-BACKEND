package com.ustu.erdbsystem.tasks.exception.response;

import com.ustu.erdbsystem.exceptions.NotFoundException;

public class TaskNotFoundException extends RuntimeException implements NotFoundException {
    public TaskNotFoundException(String message) {
        super(message);
    }

    public TaskNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

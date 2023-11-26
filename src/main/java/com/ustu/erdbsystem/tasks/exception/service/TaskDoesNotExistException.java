package com.ustu.erdbsystem.tasks.exception.service;

import com.ustu.erdbsystem.exceptions.NotFoundException;

public class TaskDoesNotExistException extends RuntimeException {
    public TaskDoesNotExistException(String message) {
        super(message);
    }
}

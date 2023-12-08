package com.ustu.erdbsystem.tasks.exception.service;

public class TaskStudentCreationException extends RuntimeException {
    public TaskStudentCreationException(String message) {
        super(message);
    }

    public TaskStudentCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}

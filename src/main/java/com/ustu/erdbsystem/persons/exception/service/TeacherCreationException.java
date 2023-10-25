package com.ustu.erdbsystem.persons.exception.service;

public class TeacherCreationException extends RuntimeException {
    public TeacherCreationException(String message) {
        super(message);
    }

    public TeacherCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}

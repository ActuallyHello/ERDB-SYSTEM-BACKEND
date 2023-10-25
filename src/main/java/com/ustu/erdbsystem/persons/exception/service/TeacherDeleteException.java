package com.ustu.erdbsystem.persons.exception.service;

public class TeacherDeleteException extends RuntimeException {
    public TeacherDeleteException(String message) {
        super(message);
    }

    public TeacherDeleteException(String message, Throwable cause) {
        super(message, cause);
    }
}

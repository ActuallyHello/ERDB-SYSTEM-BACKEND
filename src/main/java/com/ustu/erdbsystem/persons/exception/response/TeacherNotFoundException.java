package com.ustu.erdbsystem.persons.exception.response;

import com.ustu.erdbsystem.exceptions.NotFoundException;

public class TeacherNotFoundException extends RuntimeException implements NotFoundException {
    public TeacherNotFoundException(String message) {
        super(message);
    }

    public TeacherNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

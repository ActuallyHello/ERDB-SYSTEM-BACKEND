package com.ustu.erdbsystem.persons.exception.response;

public class TeacherDBException extends RuntimeException {
    public TeacherDBException(String message) {
        super(message);
    }

    public TeacherDBException(String message, Throwable cause) {
        super(message, cause);
    }
}

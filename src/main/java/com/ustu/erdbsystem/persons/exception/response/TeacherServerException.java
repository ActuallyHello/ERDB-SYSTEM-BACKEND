package com.ustu.erdbsystem.persons.exception.response;

import com.ustu.erdbsystem.persons.exception.ServerException;

public class TeacherServerException extends RuntimeException implements ServerException {
    public TeacherServerException(String message) {
        super(message);
    }

    public TeacherServerException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.ustu.erdbsystem.persons.exception.response;

import com.ustu.erdbsystem.exceptions.ServerException;

public class StudentServerException extends RuntimeException implements ServerException {
    public StudentServerException(String message) {
        super(message);
    }

    public StudentServerException(String message, Throwable cause) {
        super(message, cause);
    }
}

package com.ustu.erdbsystem.persons.exception.response;

import com.ustu.erdbsystem.exceptions.ServerException;

public class UserServerException extends RuntimeException implements ServerException {
    public UserServerException(String message) {
        super(message);
    }

    public UserServerException(String message, Throwable cause) {
        super(message, cause);
    }
}

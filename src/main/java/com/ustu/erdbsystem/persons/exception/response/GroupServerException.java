package com.ustu.erdbsystem.persons.exception.response;

import com.ustu.erdbsystem.persons.exception.ServerException;

public class GroupServerException extends RuntimeException implements ServerException {
    public GroupServerException(String message) {
        super(message);
    }

    public GroupServerException(String message, Throwable cause) {
        super(message, cause);
    }
}

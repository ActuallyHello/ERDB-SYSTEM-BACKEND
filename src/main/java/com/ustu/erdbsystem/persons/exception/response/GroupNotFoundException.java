package com.ustu.erdbsystem.persons.exception.response;

import com.ustu.erdbsystem.exceptions.NotFoundException;

public class GroupNotFoundException extends RuntimeException implements NotFoundException {
    public GroupNotFoundException(String message) {
        super(message);
    }

    public GroupNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

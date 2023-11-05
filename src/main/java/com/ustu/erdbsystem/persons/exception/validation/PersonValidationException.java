package com.ustu.erdbsystem.persons.exception.validation;

import com.ustu.erdbsystem.persons.exception.ServerException;

public class PersonValidationException extends RuntimeException implements ServerException  {
    public PersonValidationException(String message) {
        super(message);
    }

    public PersonValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}

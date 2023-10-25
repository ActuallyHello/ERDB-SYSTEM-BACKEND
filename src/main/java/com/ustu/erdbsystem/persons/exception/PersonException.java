package com.ustu.erdbsystem.persons.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class PersonException {
    private final String message;
    private final String causeClass;
    private final HttpStatus httpStatus;
    private final int httpStatusCode;
}

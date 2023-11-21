package com.ustu.erdbsystem.exceptions;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class ExceptionDTO {
    private final Object message;
    private final String causeClass;
    private final HttpStatus httpStatus;
    private final int httpStatusCode;
}

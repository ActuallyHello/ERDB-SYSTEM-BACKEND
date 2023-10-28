package com.ustu.erdbsystem.ermodels.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class ModelException {
    private final Object message;
    private final String causeClass;
    private final HttpStatus httpStatus;
    private final int httpStatusCode;
}

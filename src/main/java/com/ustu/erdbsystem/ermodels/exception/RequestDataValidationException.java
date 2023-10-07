package com.ustu.erdbsystem.ermodels.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class RequestDataValidationException extends RuntimeException {
    public RequestDataValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}

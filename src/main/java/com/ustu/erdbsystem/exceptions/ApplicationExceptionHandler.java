package com.ustu.erdbsystem.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.ustu.erdbsystem")
public class ApplicationExceptionHandler {

    @ExceptionHandler(
            value = {
                    Exception.class,
            }
    )
    public ResponseEntity<Object> handleException(Exception exception) {
        var exceptionDTO = new ExceptionDTO(
                "Internal server error",
                exception.getClass().getName(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        return new ResponseEntity<>(exceptionDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

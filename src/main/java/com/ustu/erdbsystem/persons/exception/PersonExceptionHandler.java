package com.ustu.erdbsystem.persons.exception;

import com.ustu.erdbsystem.ermodels.exception.ModelException;
import com.ustu.erdbsystem.persons.exception.response.PersonNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PersonExceptionHandler {

    @ExceptionHandler(
            value = {
                    PersonNotFoundException.class
            }
    )
    public ResponseEntity<Object> handlePersonNotFoundException(
            PersonNotFoundException personNotFoundException) {
        var personException = new PersonException(
                personNotFoundException.getMessage(),
                personNotFoundException.getClass().getSimpleName(),
                HttpStatus.NOT_FOUND,
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(personException, HttpStatus.NOT_FOUND);
    }
}

package com.ustu.erdbsystem.ermodels.exception;

import com.ustu.erdbsystem.ermodels.exception.response.ModelDBException;
import com.ustu.erdbsystem.ermodels.exception.response.ModelNotFoundException;
import com.ustu.erdbsystem.ermodels.exception.response.ModelOwnerNotFoundException;
import com.ustu.erdbsystem.ermodels.exception.response.ModelValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ModelExceptionHandler {

    @ExceptionHandler(
            value = {
                    ModelNotFoundException.class,
            }
    )
    public ResponseEntity<Object> handleModelNotFoundException(
            ModelNotFoundException modelNotFoundException) {
        var modelException = new ModelException(
                modelNotFoundException.getMessage(),
                modelNotFoundException.getClass().getSimpleName(),
                HttpStatus.NOT_FOUND,
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(modelException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(
            value = {
                    ModelOwnerNotFoundException.class
            }
    )
    public ResponseEntity<Object> handleModelOwnerNotFoundException(
            ModelOwnerNotFoundException modelOwnerNotFoundException) {
        var modelException = new ModelException(
                modelOwnerNotFoundException.getMessage(),
                modelOwnerNotFoundException.getClass().getSimpleName(),
                HttpStatus.NOT_FOUND,
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(modelException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(
            value = {
                    ModelValidationException.class,
            }
    )
    public ResponseEntity<Object> handleModelValidationException(
            ModelValidationException modelValidationException) {
        var modelException = new ModelException(
                modelValidationException.getMessage(),
                modelValidationException.getClass().getSimpleName(),
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(modelException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(
            value = {
                    ModelDBException.class
            }
    )
    public ResponseEntity<Object> handleModelDBException(
            ModelDBException modelDBException) {
        var modelException = new ModelException(
                modelDBException.getMessage(),
                modelDBException.getClass().getSimpleName(),
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(modelException, HttpStatus.BAD_REQUEST);
    }
}

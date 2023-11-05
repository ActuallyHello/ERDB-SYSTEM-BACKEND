package com.ustu.erdbsystem.ermodels.exception;

import com.ustu.erdbsystem.ermodels.exception.response.ModelNotFoundException;
import com.ustu.erdbsystem.ermodels.exception.response.ModelOwnerNotFoundException;
import com.ustu.erdbsystem.ermodels.exception.response.ModelServerException;
import com.ustu.erdbsystem.ermodels.exception.response.ModelValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ModelExceptionHandler {

    @ExceptionHandler(
            value = {
                    ModelNotFoundException.class,
                    ModelOwnerNotFoundException.class
            }
    )
    public ResponseEntity<Object> handleNotFoundException(NotFoundException notFoundException) {
        var modelException = new ModelException(
                notFoundException.getMessage(),
                notFoundException.getClass().getSimpleName(),
                HttpStatus.NOT_FOUND,
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(modelException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(
            value = {
                    ModelServerException.class,
                    ModelValidationException.class
            }
    )
    public ResponseEntity<Object> handleServerException(ServerException serverException) {
        var modelException = new ModelException(
                serverException.getMessage(),
                serverException.getClass().getSimpleName(),
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(modelException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(
            value = {
                    MethodArgumentNotValidException.class
            }
    )
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException methodArgumentNotValidException) {
        Map<String, List<String>> errorMap = methodArgumentNotValidException.getBindingResult().getFieldErrors()
                .stream()
                .collect(
                        Collectors.groupingBy(
                                FieldError::getField,
                                Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())
                        )
                );
        var modelException = new ModelException(
                errorMap,
                methodArgumentNotValidException.getClass().getSimpleName(),
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(modelException, HttpStatus.BAD_REQUEST);
    }
}

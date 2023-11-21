package com.ustu.erdbsystem.persons.exception;

import com.ustu.erdbsystem.exceptions.ExceptionDTO;
import com.ustu.erdbsystem.exceptions.NotFoundException;
import com.ustu.erdbsystem.exceptions.ServerException;
import com.ustu.erdbsystem.persons.exception.response.GroupServerException;
import com.ustu.erdbsystem.persons.exception.response.GroupNotFoundException;
import com.ustu.erdbsystem.persons.exception.response.PersonServerException;
import com.ustu.erdbsystem.persons.exception.response.PersonNotFoundException;
import com.ustu.erdbsystem.persons.exception.response.PositionServerException;
import com.ustu.erdbsystem.persons.exception.response.PositionNotFoundException;
import com.ustu.erdbsystem.persons.exception.response.StudentServerException;
import com.ustu.erdbsystem.persons.exception.response.StudentNotFoundException;
import com.ustu.erdbsystem.persons.exception.response.TeacherServerException;
import com.ustu.erdbsystem.persons.exception.response.TeacherNotFoundException;
import com.ustu.erdbsystem.persons.exception.response.UserServerException;
import com.ustu.erdbsystem.persons.exception.response.UserNotFoundException;
import com.ustu.erdbsystem.persons.exception.validation.PersonValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice(basePackages = "com.ustu.erdbsystem.persons.api.controller")
public class PersonExceptionHandler {

    @ExceptionHandler(
            value = {
                    PersonNotFoundException.class,
                    UserNotFoundException.class,
                    GroupNotFoundException.class,
                    PositionNotFoundException.class,
                    StudentNotFoundException.class,
                    TeacherNotFoundException.class,
            }
    )
    public ResponseEntity<Object> handleNotFoundException(
            NotFoundException notFoundException) {
        var personException = new ExceptionDTO(
                notFoundException.getMessage(),
                notFoundException.getClass().getSimpleName(),
                HttpStatus.NOT_FOUND,
                HttpStatus.NOT_FOUND.value()
        );
        return new ResponseEntity<>(personException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(
            value = {
                    PersonServerException.class,
                    PersonValidationException.class,
                    UserServerException.class,
                    GroupServerException.class,
                    PositionServerException.class,
                    StudentServerException.class,
                    TeacherServerException.class,
            }
    )
    public ResponseEntity<Object> handleServerException(
            ServerException serverException) {
        var personException = new ExceptionDTO(
                serverException.getMessage(),
                serverException.getClass().getSimpleName(),
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(personException, HttpStatus.BAD_REQUEST);
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
        var personException = new ExceptionDTO(
                errorMap,
                methodArgumentNotValidException.getClass().getSimpleName(),
                HttpStatus.BAD_REQUEST,
                HttpStatus.BAD_REQUEST.value()
        );
        return new ResponseEntity<>(personException, HttpStatus.BAD_REQUEST);
    }
}

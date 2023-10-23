package com.ustu.erdbsystem.ermodels.exception.service;

public class ModelDoesNotExistException extends RuntimeException {
    public ModelDoesNotExistException(String message) {
        super(message);
    }
}

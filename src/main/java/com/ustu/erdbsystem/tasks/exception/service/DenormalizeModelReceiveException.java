package com.ustu.erdbsystem.tasks.exception.service;

public class DenormalizeModelReceiveException extends RuntimeException {
    public DenormalizeModelReceiveException(String message) {
        super(message);
    }

    public DenormalizeModelReceiveException(String message, Throwable cause) {
        super(message, cause);
    }
}

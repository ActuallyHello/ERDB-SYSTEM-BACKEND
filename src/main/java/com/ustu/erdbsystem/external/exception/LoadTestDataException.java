package com.ustu.erdbsystem.external.exception;

public class LoadTestDataException extends RuntimeException {
    public LoadTestDataException(String message) {
        super(message);
    }

    public LoadTestDataException(String message, Throwable cause) {
        super(message, cause);
    }
}

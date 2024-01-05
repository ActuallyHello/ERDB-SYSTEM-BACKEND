package com.ustu.erdbsystem.external.exception;

public class UploadTestDataException extends RuntimeException {
    public UploadTestDataException(String message) {
        super(message);
    }

    public UploadTestDataException(String message, Throwable cause) {
        super(message, cause);
    }
}

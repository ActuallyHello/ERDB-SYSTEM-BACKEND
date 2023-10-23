package com.ustu.erdbsystem.ermodels.exception.response;

public class ModelDBException extends RuntimeException {
    public ModelDBException(String message) {
        super(message);
    }

    public ModelDBException(String message, Throwable cause) {
        super(message, cause);
    }
}

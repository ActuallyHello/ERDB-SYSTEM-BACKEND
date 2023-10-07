package com.ustu.erdbsystem.ermodels.exception;

public class RelationDoesNotMatchEntityException extends RuntimeException{
    public RelationDoesNotMatchEntityException(String message) {
        super(message);
    }
}

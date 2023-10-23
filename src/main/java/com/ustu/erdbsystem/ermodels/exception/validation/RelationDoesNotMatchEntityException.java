package com.ustu.erdbsystem.ermodels.exception.validation;

public class RelationDoesNotMatchEntityException extends RuntimeException{
    public RelationDoesNotMatchEntityException(String message) {
        super(message);
    }
}

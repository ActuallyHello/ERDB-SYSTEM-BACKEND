package com.ustu.erdbsystem.ermodels.factory;

public interface DTOFactory<T, E> {
    T makeDTO(E e);
}
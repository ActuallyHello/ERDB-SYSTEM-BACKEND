package com.ustu.erdbsystem.persons.factory;

public interface DTOFactory<T, E> {
    T makeDTO(E e);
}

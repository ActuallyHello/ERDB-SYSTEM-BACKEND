package com.ustu.erdbsystem.persons.api.mapper;

public interface DTOMapper<T, E> {
    T makeDTO(E e);
}

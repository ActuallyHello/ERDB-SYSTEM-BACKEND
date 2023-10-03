package com.ustu.erdbsystem.ermodels.api.mapper;

public interface DTOMapper<T, E> {
    T makeDTO(E e);
}
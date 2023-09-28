package com.ustu.erdbsystem.persons.store.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PersonType {
    TEACHER("TEACHER"), STUDENT("STUDENT");

    private final String personType;
}

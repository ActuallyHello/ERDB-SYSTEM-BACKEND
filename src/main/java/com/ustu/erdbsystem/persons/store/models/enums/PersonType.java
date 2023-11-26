package com.ustu.erdbsystem.persons.store.models.enums;

import com.ustu.erdbsystem.ermodels.exception.validation.EnumValueException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PersonType {
    TEACHER("TEACHER"), STUDENT("STUDENT");

    private final String personType;

    public static PersonType fromString(String personType) {
        for (var p : PersonType.values()) {
            if (p.personType.equalsIgnoreCase(personType)) {
                return p;
            }
        }
        throw new EnumValueException("No such enum PersonType with value '%s'! [ValidationException]".formatted(personType));
    }

    public String getValue() {
        return personType;
    }
}

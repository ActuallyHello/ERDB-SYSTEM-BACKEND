package com.ustu.erdbsystem.persons.store.models.enums;

import com.ustu.erdbsystem.ermodels.exception.validation.EnumValueException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum PersonType {
    TEACHER("TEACHER"), STUDENT("STUDENT");

    private final String personType;

    public static PersonType fromString(String shortPower) {
        for (var p : PersonType.values()) {
            if (p.personType.equalsIgnoreCase(shortPower)) {
                return p;
            }
        }
        throw new EnumValueException("NO SUCH ENUM POWER WITH VALUE \"%s\"!".formatted(shortPower));
    }

    public String getValue() {
        return personType;
    }
}

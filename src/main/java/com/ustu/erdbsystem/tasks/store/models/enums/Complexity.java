package com.ustu.erdbsystem.tasks.store.models.enums;

import com.ustu.erdbsystem.ermodels.exception.validation.EnumValueException;
import com.ustu.erdbsystem.persons.store.models.enums.PersonType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Complexity {
    EASY("EASY"), NORMAL("NORMAL"), DIFFICULT("DIFFICULT");

    private final String complexity;

    public static Complexity fromString(String complexity) {
        for (var c : Complexity.values()) {
            if (c.complexity.equalsIgnoreCase(complexity)) {
                return c;
            }
        }
        throw new EnumValueException("No such enum Complexity with value '%s'! [ValidationException]".formatted(complexity));
    }

    public String getValue() {
        return complexity;
    }
}

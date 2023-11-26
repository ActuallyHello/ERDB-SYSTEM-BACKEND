package com.ustu.erdbsystem.tasks.store.models.enums;

import com.ustu.erdbsystem.ermodels.exception.validation.EnumValueException;
import com.ustu.erdbsystem.persons.store.models.enums.PersonType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Mark {
    EXCELLENT("5"), BAD("2"), SATISFACTORY("3"), GOOD("4");

    private final String mark;

    public static Mark fromString(String mark) {
        for (var m : Mark.values()) {
            if (m.mark.equalsIgnoreCase(mark)) {
                return m;
            }
        }
        throw new EnumValueException("No such enum PersonType with value '%s'! [ValidationException]".formatted(mark));
    }

    public String getValue() {
        return mark;
    }
}

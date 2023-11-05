package com.ustu.erdbsystem.ermodels.store.models.enums;

import com.ustu.erdbsystem.ermodels.exception.validation.EnumValueException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AttributeType {
    PRIMARY_KEY("PK"), FOREIGN_KEY("FK"), ATTRIBUTE("ATTR");

    private final String attributeType;

    public static AttributeType fromString(String shortAttributePower) {
        for (var at : AttributeType.values()) {
            if (at.attributeType.equalsIgnoreCase(shortAttributePower)) {
                return at;
            }
        }
        throw new EnumValueException("No such enum AttributeType with value '%s'! [ValidationException]".formatted(shortAttributePower));
    }

    public String getValue() {
        return attributeType;
    }
}

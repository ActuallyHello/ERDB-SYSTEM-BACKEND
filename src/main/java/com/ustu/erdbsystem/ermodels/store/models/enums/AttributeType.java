package com.ustu.erdbsystem.ermodels.store.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AttributeType {
    PRIMARY_KEY("PK"), FOREIGN_KEY("FK"), ATTRIBUTE("ATTR");

    private final String attributeType;
}

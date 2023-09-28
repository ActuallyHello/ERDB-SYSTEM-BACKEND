package com.ustu.erdbsystem.ermodels.store.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Power {
    ONE_TO_ONE("1-1"), ONE_TO_MANY("1-N"), MANY_TO_ONE("N-1");

    private final String power;
}

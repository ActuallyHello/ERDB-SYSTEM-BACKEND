package com.ustu.erdbsystem.ermodels.store.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Power {
    ONE_TO_ONE("1-1"), ONE_TO_MANY("1-N"), MANY_TO_ONE("N-1");

    private final String power;

    public static Power fromString(String shortPower) {
        for (var p : Power.values()) {
            if (p.power.equalsIgnoreCase(shortPower)) {
                return p;
            }
        }
        throw new IllegalArgumentException("NO SUCH ENUM POWER WITH VALUE \"%s\"!".formatted(shortPower));
    }

    public String getValue() {
        return power;
    }
}

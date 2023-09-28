package com.ustu.erdbsystem.tasks.store.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Mark {
    EXCELLENT("5"), BAD("2"), SATISFACTORY("3"), GOOD("4");

    private final String mark;
}

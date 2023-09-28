package com.ustu.erdbsystem.tasks.store.models.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Complexity {
    EASY("EASY"), NORMAL("NORMAL"), DIFFICULT("DIFFICULT");

    private final String complexity;
}

package com.ustu.erdbsystem.tasks.api.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class TestDataDTO {
    private final List<String> entities = new ArrayList<>();
    private final List<String> attributes = new ArrayList<>();
    private final List<List<String>> testData = new ArrayList<>();
}

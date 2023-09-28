package com.ustu.erdbsystem.tasks.store.models.converters;

import com.ustu.erdbsystem.tasks.store.models.enums.Complexity;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class ComplexityConverter implements AttributeConverter<Complexity, String> {
    @Override
    public String convertToDatabaseColumn(Complexity complexity) {
        if (complexity == null) {
            return null;
        }
        return complexity.getComplexity();
    }

    @Override
    public Complexity convertToEntityAttribute(String complexity) {
        if (complexity == null) {
            return null;
        }
        return Stream.of(Complexity.values())
                .filter(at -> at.getComplexity().equals(complexity))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

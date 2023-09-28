package com.ustu.erdbsystem.tasks.store.models.converters;

import com.ustu.erdbsystem.tasks.store.models.enums.Mark;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class MarkConverter implements AttributeConverter<Mark, String> {

    @Override
    public String convertToDatabaseColumn(Mark mark) {
        if (mark == null) {
            return null;
        }
        return mark.getMark();
    }

    @Override
    public Mark convertToEntityAttribute(String mark) {
        if (mark == null) {
            return null;
        }
        return Stream.of(Mark.values())
                .filter(at -> at.getMark().equals(mark))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

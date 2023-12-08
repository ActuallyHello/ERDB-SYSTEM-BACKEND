package com.ustu.erdbsystem.ermodels.store.models.converters;

import com.ustu.erdbsystem.ermodels.store.models.enums.AttributeType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class AttributeTypeConverter implements AttributeConverter<AttributeType, String> {
    @Override
    public String convertToDatabaseColumn(AttributeType attributeType) {
        if (attributeType == null) {
            return null;
        }
        return attributeType.getValue();
    }

    @Override
    public AttributeType convertToEntityAttribute(String attributeType) {
        if (attributeType == null) {
            return null;
        }
        return Stream.of(AttributeType.values())
                .filter(at -> at.getValue().equals(attributeType))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

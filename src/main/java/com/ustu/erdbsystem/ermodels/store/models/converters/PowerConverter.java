package com.ustu.erdbsystem.ermodels.store.models.converters;

import com.ustu.erdbsystem.ermodels.store.models.enums.Power;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class PowerConverter implements AttributeConverter<Power, String> {

    @Override
    public String convertToDatabaseColumn(Power power) {
        if (power == null) {
            return null;
        }
        return power.getValue();
    }

    @Override
    public Power convertToEntityAttribute(String power) {
        if (power == null) {
            return null;
        }
        return Stream.of(Power.values())
                .filter(at -> at.getValue().equals(power))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

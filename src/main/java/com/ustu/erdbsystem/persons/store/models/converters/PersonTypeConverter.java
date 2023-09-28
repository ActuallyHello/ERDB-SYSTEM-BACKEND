package com.ustu.erdbsystem.persons.store.models.converters;

import com.ustu.erdbsystem.persons.store.models.enums.PersonType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class PersonTypeConverter implements AttributeConverter<PersonType, String> {
    @Override
    public String convertToDatabaseColumn(PersonType personType) {
        if (personType == null) {
            return null;
        }
        return personType.getPersonType();
    }

    @Override
    public PersonType convertToEntityAttribute(String personType) {
        if (personType == null) {
            return null;
        }
        return Stream.of(PersonType.values())
                .filter(at -> at.getPersonType().equals(personType))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

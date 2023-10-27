package com.ustu.erdbsystem.persons.api.mapper;

import com.ustu.erdbsystem.persons.api.dto.PersonDTO;
import com.ustu.erdbsystem.persons.api.dto.request.CreatePersonRequestDTO;
import com.ustu.erdbsystem.persons.store.models.Person;
import com.ustu.erdbsystem.persons.store.models.enums.PersonType;

import java.util.Objects;

public class PersonDTOMapper {
    public static PersonDTO makeDTO(Person person) {
        if (person == null) throw new IllegalArgumentException("person is null!");
        return PersonDTO.builder()
                .id(person.getId())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .middleName(person.getMiddleName())
                .personType(person.getPersonType())
                .build();
    }

    public static PersonDTO makeDTO(CreatePersonRequestDTO createPersonRequestDTO) {
        if (createPersonRequestDTO == null) throw new IllegalArgumentException("createPersonRequestDTO is null!");
        return PersonDTO.builder()
                .firstName(createPersonRequestDTO.getFirstName())
                .lastName(createPersonRequestDTO.getLastName())
                .middleName(createPersonRequestDTO.getMiddleName())
                .personType(PersonType.fromString(createPersonRequestDTO.getPersonType()))
                .build();
    }

    public static Person fromDTO(PersonDTO personDTO) {
        if (personDTO == null) throw new IllegalArgumentException("personDTO is null!");
        return Person.builder()
                .id(personDTO.getId())
                .firstName(personDTO.getFirstName())
                .lastName(personDTO.getLastName())
                .middleName(personDTO.getMiddleName())
                .personType(personDTO.getPersonType())
                .build();
    }
}

package com.ustu.erdbsystem.persons.api.mapper;

import com.ustu.erdbsystem.persons.api.dto.PersonCredentialsDTO;
import com.ustu.erdbsystem.persons.store.models.Person;

public class PersonCredentialsDTOMapper {
    public static PersonCredentialsDTO makeDTO(Person person) {
        if (person == null) throw new IllegalArgumentException("person is null!");
        return PersonCredentialsDTO.builder()
                .id(person.getId())
                .firstName(person.getFirstName())
                .lastName(person.getLastName())
                .middleName(person.getMiddleName())
                .personType(person.getPersonType())
                .build();
    }
}

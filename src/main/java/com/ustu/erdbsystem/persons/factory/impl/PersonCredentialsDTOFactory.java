package com.ustu.erdbsystem.persons.factory.impl;

import com.ustu.erdbsystem.ermodels.factory.DTOFactory;
import com.ustu.erdbsystem.persons.dto.PersonCredentialsDTO;
import com.ustu.erdbsystem.persons.store.models.Person;
import org.springframework.stereotype.Component;

@Component
public class PersonCredentialsDTOFactory implements DTOFactory<PersonCredentialsDTO, Person> {
    @Override
    public PersonCredentialsDTO makeDTO(Person person) {
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

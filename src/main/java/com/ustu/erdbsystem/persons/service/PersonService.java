package com.ustu.erdbsystem.persons.service;

import com.ustu.erdbsystem.persons.api.dto.PersonDTO;
import com.ustu.erdbsystem.persons.api.dto.request.CreatePersonRequestDTO;
import com.ustu.erdbsystem.persons.store.models.Person;
import com.ustu.erdbsystem.persons.store.models.User;

import java.util.List;
import java.util.Optional;

public interface PersonService {

    List<Person> getAll();

    Optional<Person> getById(Long id);

    Optional<Person> getByIdWithModels(Long id);

    Optional<Person> getByUser(User user);

    Person create(PersonDTO personDTO, User user);

    void delete(Person person);

    Person update(Person person, User user, PersonDTO personDTO);
}

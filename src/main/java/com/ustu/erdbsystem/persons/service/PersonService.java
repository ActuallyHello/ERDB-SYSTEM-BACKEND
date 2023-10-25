package com.ustu.erdbsystem.persons.service;

import com.ustu.erdbsystem.persons.api.dto.PersonDTO;
import com.ustu.erdbsystem.persons.store.models.Person;
import com.ustu.erdbsystem.persons.store.models.Position;
import com.ustu.erdbsystem.persons.store.models.Teacher;
import com.ustu.erdbsystem.persons.store.models.User;

import java.util.List;
import java.util.Optional;

public interface PersonService {
    Optional<Person> getById(Long id);

    Optional<Person> getByUser(User user);

    Person create(PersonDTO personDTO, User user);

    void delete(Person person);

    Person update(Person personNew);
}

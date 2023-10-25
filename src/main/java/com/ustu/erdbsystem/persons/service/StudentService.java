package com.ustu.erdbsystem.persons.service;

import com.ustu.erdbsystem.persons.store.models.Group;
import com.ustu.erdbsystem.persons.store.models.Person;
import com.ustu.erdbsystem.persons.store.models.Student;

import java.util.Optional;

public interface StudentService {
    Optional<Student> getById(Long id);

    Optional<Student> getByPerson(Person person);

    Student create(Person person, Group group);

    void delete(Student student);

    Student update(Student studentNew);
}

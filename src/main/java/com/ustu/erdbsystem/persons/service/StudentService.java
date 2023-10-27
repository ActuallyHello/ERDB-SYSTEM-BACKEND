package com.ustu.erdbsystem.persons.service;

import com.ustu.erdbsystem.persons.store.models.Group;
import com.ustu.erdbsystem.persons.store.models.Person;
import com.ustu.erdbsystem.persons.store.models.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    List<Student> getAllByGroupIdWithPerson(Long groupId);

    Optional<Student> getById(Long id);

    Optional<Student> getByIdWithPersonAndGroup(Long id);

    Optional<Student> getByPersonIdWithGroup(Long personId);

    Student create(Person person, Group group);

    void delete(Student student);

    Student update(Student studentNew);
}

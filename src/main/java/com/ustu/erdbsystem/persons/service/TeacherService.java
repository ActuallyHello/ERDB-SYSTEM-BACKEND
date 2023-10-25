package com.ustu.erdbsystem.persons.service;

import com.ustu.erdbsystem.persons.store.models.Person;
import com.ustu.erdbsystem.persons.store.models.Position;
import com.ustu.erdbsystem.persons.store.models.Teacher;

import java.util.List;
import java.util.Optional;

public interface TeacherService {
    List<Teacher> getAll();

    Optional<Teacher> getById(Long id);

    Optional<Teacher> getByPerson(Person person);

    Teacher create(Person person, Position position);

    void delete(Teacher teacher);

    Teacher update(Teacher teacherNew);
}

package com.ustu.erdbsystem.persons.service;

import com.ustu.erdbsystem.persons.api.dto.TeacherDTO;
import com.ustu.erdbsystem.persons.store.models.Person;
import com.ustu.erdbsystem.persons.store.models.Position;
import com.ustu.erdbsystem.persons.store.models.Teacher;

import java.util.List;
import java.util.Optional;

public interface TeacherService {

    TeacherDTO getTeacherDTOByPerson(Person person);


    List<Teacher> getAll();

    List<Teacher> getAllByPositionId(Long positionId);

    Optional<Teacher> getById(Long id);

    Optional<Teacher> getByIdWithResults(Long id);

    Optional<Teacher> getByIdWithTasks(Long id);

    Optional<Teacher> getByIdWithPersonAndPosition(Long id);

    Optional<Teacher> getByPersonIdWithPosition(Long personId);

    Optional<Teacher> getByPerson(Person person);

    Teacher create(Person person, Position position);

    void delete(Teacher teacher);

    Teacher update(Teacher teacherNew);

}

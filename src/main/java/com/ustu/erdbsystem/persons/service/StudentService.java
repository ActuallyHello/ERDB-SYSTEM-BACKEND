package com.ustu.erdbsystem.persons.service;

import com.ustu.erdbsystem.persons.api.dto.request.CreateGroupRequestDTO;
import com.ustu.erdbsystem.persons.store.models.Group;
import com.ustu.erdbsystem.persons.store.models.Person;
import com.ustu.erdbsystem.persons.store.models.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    Long saveStudent(Person person, Group group);
    Student getById(Long id);
    List<Group> getStudentGroups(Boolean isActive);
    Optional<Student> getStudentByPerson(Person person);
    Long saveGroup(CreateGroupRequestDTO group);

}

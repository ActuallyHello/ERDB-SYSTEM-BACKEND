package com.ustu.erdbsystem.persons.service;

import com.ustu.erdbsystem.persons.api.dto.PersonCredentialsDTO;
import com.ustu.erdbsystem.persons.api.dto.request.CreateGroupRequestDTO;
import com.ustu.erdbsystem.persons.api.dto.request.CreateUserRequestDTO;
import com.ustu.erdbsystem.persons.store.models.Group;
import com.ustu.erdbsystem.persons.store.models.Person;
import com.ustu.erdbsystem.persons.store.models.Position;
import com.ustu.erdbsystem.persons.store.models.Student;
import com.ustu.erdbsystem.persons.store.models.Teacher;
import com.ustu.erdbsystem.persons.store.models.User;

import java.util.List;
import java.util.Optional;

public interface PersonService {
    Optional<Person> getPersonById(Long id);
    Optional<User> getUserById(Long id);
    List<Position> getTeacherPositions();
    Optional<Teacher> getTeacherByPerson(Person person);
    Long saveUser(CreateUserRequestDTO userDTO);
    Long savePerson(User user, PersonCredentialsDTO personDTO);
    Long saveTeacher(Person person, Position position);
}

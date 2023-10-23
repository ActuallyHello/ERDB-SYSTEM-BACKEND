package com.ustu.erdbsystem.persons.service.impl;

import com.ustu.erdbsystem.persons.api.dto.PersonCredentialsDTO;
import com.ustu.erdbsystem.persons.api.dto.request.CreateGroupRequestDTO;
import com.ustu.erdbsystem.persons.api.dto.request.CreateUserRequestDTO;
import com.ustu.erdbsystem.persons.service.PersonService;
import com.ustu.erdbsystem.persons.store.models.Group;
import com.ustu.erdbsystem.persons.store.models.Person;
import com.ustu.erdbsystem.persons.store.models.Position;
import com.ustu.erdbsystem.persons.store.models.Student;
import com.ustu.erdbsystem.persons.store.models.Teacher;
import com.ustu.erdbsystem.persons.store.models.User;
import com.ustu.erdbsystem.persons.store.models.enums.PersonType;
import com.ustu.erdbsystem.persons.store.repos.GroupRepo;
import com.ustu.erdbsystem.persons.store.repos.PersonRepo;
import com.ustu.erdbsystem.persons.store.repos.PositionRepo;
import com.ustu.erdbsystem.persons.store.repos.StudentRepo;
import com.ustu.erdbsystem.persons.store.repos.TeacherRepo;
import com.ustu.erdbsystem.persons.store.repos.UserRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
public class PersonServiceImpl implements PersonService {

    private UserRepo userRepo;
    private PersonRepo personRepo;
    private GroupRepo groupRepo;
    private StudentRepo studentRepo;
    private TeacherRepo teacherRepo;
    private PositionRepo positionRepo;

    @Override
    @Transactional
    public Optional<Person> getPersonById(Long id) {
        log.info("");
        return personRepo.findById(id);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepo.findById(id);
    }

//    @Override
    @Transactional
    public List<Group> getStudentGroups(Boolean isActive) {
        return groupRepo.findByIsActive(isActive);
    }

    @Override
    @Transactional
    public List<Position> getTeacherPositions() {
        return positionRepo.findAll();
    }

//    @Override
    @Transactional
    public Optional<Student> getStudentByPerson(Person person) {
        return studentRepo.findByPerson(person);
    }

    @Override
    @Transactional
    public Optional<Teacher> getTeacherByPerson(Person person) {
        return teacherRepo.findByPerson(person);
    }

    @Override
    @Transactional
    public Long saveUser(CreateUserRequestDTO userDTO) {
        var user = userRepo.saveAndFlush(User.builder()
                .login(userDTO.getLogin())
                .password(userDTO.getPassword())
                .email(userDTO.getEmail())
                .build()
        );
        return user.getId();
    }

    @Override
    @Transactional
    public Long savePerson(User user, PersonCredentialsDTO personDTO) {
        var person = personRepo.saveAndFlush(Person.builder()
                .firstName(personDTO.getFirstName())
                .lastName(personDTO.getLastName())
                .middleName(personDTO.getMiddleName())
                .personType(PersonType.fromString(personDTO.getPersonType()))
                .user(user)
                .build()
        );
        return person.getId();
    }

//    @Override
    @Transactional
    public Long saveStudent(Person person, Group group) {
        var student = studentRepo.saveAndFlush(Student.builder()
                .person(person)
                .group(group)
                .build());
        return student.getId();
    }

    @Override
    @Transactional
    public Long saveTeacher(Person person, Position position) {
        var teacher = teacherRepo.saveAndFlush(Teacher.builder()
                .person(person)
                .position(position)
                .build());
        return teacher.getId();
    }

//    @Override
    @Transactional
    public Long saveGroup(CreateGroupRequestDTO group) {
        return null;
    }

}

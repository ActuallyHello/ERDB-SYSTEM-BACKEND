package com.ustu.erdbsystem.persons.service.impl;

import com.ustu.erdbsystem.persons.exception.service.StudentCreationException;
import com.ustu.erdbsystem.persons.exception.service.StudentDeleteException;
import com.ustu.erdbsystem.persons.service.StudentService;
import com.ustu.erdbsystem.persons.store.models.Group;
import com.ustu.erdbsystem.persons.store.models.Person;
import com.ustu.erdbsystem.persons.store.models.Student;
import com.ustu.erdbsystem.persons.store.repos.StudentRepo;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepo studentRepo;

    @Override
    @Transactional
    public Optional<Student> getById(Long id) {
        var student = studentRepo.findById(id);
        log.info("GET STUDENT WITH ID={}", id);
        return student;
    }

    @Override
    @Transactional
    public Optional<Student> getByPerson(Person person) {
        var student = studentRepo.findByPerson(person);
        log.info("GET STUDENT BY PERSON WITH ID={}", person.getId());
        return student;
    }

    @Override
    @Transactional
    public Student create(Person person, Group group) {
        var student = Student.builder()
                .person(person)
                .build();
        group.addStudent(student);
        try {
            student = studentRepo.saveAndFlush(student);
            log.info("STUDENT WITH ID={} WAS CREATED", student.getId());
            return student;
        } catch (PersistenceException exception) {
            log.error("CANNOT CREATE STUDENT: {}", exception.getMessage());
            throw new StudentCreationException(exception.getMessage(), exception);
        }
    }

    @Override
    public void delete(Student student) {
        try {
            studentRepo.delete(student);
            log.info("STUDENT WITH ID={} WAS DELETED", student.getId());
        } catch (PersistenceException exception) {
            log.error("CANNOT DELETE STUDENT: {}", exception.getMessage());
            throw new StudentDeleteException(exception.getMessage(), exception);
        }
    }

    @Override
    public Student update(Student studentNew) {
        try {
            var student = studentRepo.saveAndFlush(studentNew);
            log.info("STUDENT WITH ID={} WAS UPDATED", studentNew.getId());
            return student;
        } catch (PersistenceException exception) {
            log.error("CANNOT UPDATE STUDENT: {}", exception.getMessage());
            throw new StudentCreationException(exception.getMessage(), exception);
        }
    }
}
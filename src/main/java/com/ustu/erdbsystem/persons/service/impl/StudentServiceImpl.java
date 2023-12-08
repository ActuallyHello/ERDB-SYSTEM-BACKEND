package com.ustu.erdbsystem.persons.service.impl;

import com.ustu.erdbsystem.persons.exception.service.StudentCreationException;
import com.ustu.erdbsystem.persons.exception.service.StudentDeleteException;
import com.ustu.erdbsystem.persons.service.StudentService;
import com.ustu.erdbsystem.persons.store.models.Group;
import com.ustu.erdbsystem.persons.store.models.Person;
import com.ustu.erdbsystem.persons.store.models.Student;
import com.ustu.erdbsystem.persons.store.repos.StudentRepo;
import jakarta.persistence.PersistenceException;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class StudentServiceImpl implements StudentService {

    private StudentRepo studentRepo;

    @Override
    public List<Student> getAllByGroupIdWithPerson(Long groupId) {
        var studentList = studentRepo.findAllByGroupIdWithPerson(groupId);
        log.info("GET STUDENTS BY GROUP WITH ID={} ({})", groupId, studentList.size());
        return studentList;
    }

    @Override
    public Optional<Student> getById(Long id) {
        var student = studentRepo.findById(id);
        log.info("GET STUDENT WITH ID={}", id);
        return student;
    }

    @Override
    public Optional<Student> getByIdWithPersonAndGroup(Long id) {
        var student = studentRepo.findByIdWithPersonAndGroup(id);
        log.info("GET STUDENT WITH ID={}", id);
        return student;
    }

    @Override
    public Optional<Student> getByIdWithGroup(Long id) {
        var student = studentRepo.findByPersonIdWithGroup(id);
        log.info("GET STUDENT BY PERSON WITH ID={}", id);
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
        } catch (DataIntegrityViolationException | PersistenceException exception) {
            log.error("ERROR WHEN CREATING STUDENT: {}", exception.getMessage());
            throw new StudentCreationException("Error when creating student! [DatabaseException]", exception);
        }
    }

    @Override
    @Transactional
    public void delete(Student student) {
        try {
            studentRepo.delete(student);
            studentRepo.flush();
            log.info("STUDENT WITH ID={} WAS DELETED", student.getId());
        } catch (DataIntegrityViolationException exception) {
            log.error("ERROR WHEN DELETING STUDENT: {}", exception.getMessage());
            throw new StudentDeleteException("Error when deleting student! [DatabaseException]", exception);
        }
    }

    @Override
    @Transactional
    public Student update(Student studentNew) {
        try {
            var student = studentRepo.saveAndFlush(studentNew);
            log.info("STUDENT WITH ID={} WAS UPDATED", studentNew.getId());
            return student;
        } catch (DataIntegrityViolationException exception) {
            log.error("ERROR WHEN UPDATING STUDENT: {}", exception.getMessage());
            throw new StudentCreationException("Error when updating student! [DatabaseException]", exception);
        }
    }
}

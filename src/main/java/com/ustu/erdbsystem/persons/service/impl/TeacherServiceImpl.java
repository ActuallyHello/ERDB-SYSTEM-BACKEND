package com.ustu.erdbsystem.persons.service.impl;

import com.ustu.erdbsystem.persons.exception.service.TeacherCreationException;
import com.ustu.erdbsystem.persons.exception.service.TeacherDeleteException;
import com.ustu.erdbsystem.persons.service.TeacherService;
import com.ustu.erdbsystem.persons.store.models.Person;
import com.ustu.erdbsystem.persons.store.models.Position;
import com.ustu.erdbsystem.persons.store.models.Teacher;
import com.ustu.erdbsystem.persons.store.repos.TeacherRepo;
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
public class TeacherServiceImpl implements TeacherService {

    private TeacherRepo teacherRepo;

    @Override
    public List<Teacher> getAll() {
        var teacherList = teacherRepo.findAll();
        log.info("GET TEACHER ({})", teacherList.size());
        return teacherList;
    }

    @Override
    public List<Teacher> getAllByPositionId(Long positionId) {
        var teacherList = teacherRepo.findAllByPositionIdWithPerson(positionId);
        log.info("GET TEACHER BY POSITION WITH ID={} ({})", positionId, teacherList.size());
        return teacherList;
    }

    @Override
    public Optional<Teacher> getById(Long id) {
        var teacher = teacherRepo.findById(id);
        log.info("GET TEACHER WITH ID={}", id);
        return teacher;
    }

    @Override
    public Optional<Teacher> getByIdWithPersonAndPosition(Long id) {
        var teacher = teacherRepo.findByIdWithPersonAndPosition(id);
        log.info("GET TEACHER WITH ID={}", id);
        return teacher;
    }

    @Override
    public Optional<Teacher> getByPersonIdWithPosition(Long personId) {
        var teacher = teacherRepo.findByPersonIdWithPosition(personId);
        log.info("GET TEACHER BY PERSON WITH ID={}", personId);
        return teacher;
    }

    @Override
    public Optional<Teacher> getByPerson(Person person) {
        var teacher = teacherRepo.findByPerson(person);
        log.info("GET TEACHER BY PERSON WITH ID={}", person.getId());
        return teacher;
    }

    @Override
    @Transactional
    public Teacher create(Person person, Position position) {
        var teacher = Teacher.builder()
                .person(person)
                .build();
        position.addTeacher(teacher);
        try {
            teacher = teacherRepo.saveAndFlush(teacher);
            log.info("CREATE TEACHER WITH ID={}", teacher.getId());
            return teacher;
        } catch (DataIntegrityViolationException | PersistenceException exception) {
            log.error("ERROR WHEN CREATING TEACHER: {}", exception.getMessage());
            throw new TeacherCreationException("Error when creating teacher! [DatabaseException]", exception);
        }
    }

    @Override
    @Transactional
    public void delete(Teacher teacher) {
        try {
            teacherRepo.delete(teacher);
            teacherRepo.flush();
            log.info("TEACHER WITH ID={} WAS DELETED", teacher.getId());
        } catch (DataIntegrityViolationException | PersistenceException exception) {
            log.error("ERROR WHEN DELETING TEACHER: {}", exception.getMessage());
            throw new TeacherDeleteException("Error when deleting teacher! [DatabaseException]", exception);
        }
    }

    @Override
    @Transactional
    public Teacher update(Teacher teacherNew) {
        try {
            var teacher = teacherRepo.saveAndFlush(teacherNew);
            log.info("TEACHER WITH ID={} WAS UPDATED", teacher.getId());
            return teacher;
        } catch (DataIntegrityViolationException exception) {
            log.error("ERROR WHEN UPDATING TEACHER: {}", exception.getMessage());
            throw new TeacherDeleteException("Error when updating teacher! [DatabaseException]", exception);
        }
    }
}

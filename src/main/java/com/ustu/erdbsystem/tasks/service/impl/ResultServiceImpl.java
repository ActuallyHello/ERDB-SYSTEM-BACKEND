package com.ustu.erdbsystem.tasks.service.impl;

import com.ustu.erdbsystem.ermodels.api.dto.response.ModelDetailDTO;
import com.ustu.erdbsystem.ermodels.service.ModelService;
import com.ustu.erdbsystem.ermodels.store.models.Model;
import com.ustu.erdbsystem.persons.api.dto.StudentDTO;
import com.ustu.erdbsystem.persons.api.dto.TeacherDTO;
import com.ustu.erdbsystem.persons.api.mapper.PersonDTOMapper;
import com.ustu.erdbsystem.persons.api.mapper.PositionDTOMapper;
import com.ustu.erdbsystem.persons.api.mapper.TeacherDTOMapper;
import com.ustu.erdbsystem.persons.service.StudentService;
import com.ustu.erdbsystem.persons.store.models.Person;
import com.ustu.erdbsystem.persons.store.models.Teacher;
import com.ustu.erdbsystem.tasks.api.dtos.TaskDTO;
import com.ustu.erdbsystem.tasks.api.dtos.response.ResultWithModelDTO;
import com.ustu.erdbsystem.tasks.api.dtos.response.ResultWithTaskDTO;
import com.ustu.erdbsystem.tasks.api.mapper.ResultWithModelDTOMapper;
import com.ustu.erdbsystem.tasks.api.mapper.ResultWithTaskDTOMapper;
import com.ustu.erdbsystem.tasks.api.mapper.TaskDTOMapper;
import com.ustu.erdbsystem.tasks.exception.service.ResultCreationException;
import com.ustu.erdbsystem.tasks.service.ResultService;
import com.ustu.erdbsystem.tasks.store.models.Result;
import com.ustu.erdbsystem.tasks.store.models.Task;
import com.ustu.erdbsystem.tasks.store.models.enums.Mark;
import com.ustu.erdbsystem.tasks.store.repos.ResultRepo;
import jakarta.persistence.PersistenceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class ResultServiceImpl implements ResultService {

    private final ResultRepo resultRepo;

    @Override
    public List<Result> getAllWithTaskAndModelAndTeacher(Pageable pageable) {
        var resultList = resultRepo.findAllWithTaskAndModelAndTeacher(pageable);
        log.debug("GET RESULTS ({}) PAGE={} SIZE={}", resultList.size(),
                pageable.getPageNumber(), pageable.getPageSize());
        return resultList;
    }

    @Override
    public Optional<Result> getById(Long id) {
        var result = resultRepo.findById(id);
        log.debug("GET RESULT (ID={})", id);
        return result;
    }

    @Override
    public Optional<Result> getByIdWithModelAndTaskAndTeacher(Long id) {
        var result = resultRepo.findByIdWithModelAndTaskAndTeacher(id);
        log.debug("GET RESULT (ID={})", id);
        return result;
    }

    @Override
    public Optional<Result> getLastByPersonAndTask(Person person, Task task) {
        var result = resultRepo.findLastByPersonAndTask(person, task);
        log.debug("GET RESULT BY PERSON (ID={})", person.getId());
        return result;
    }

    @Override
    @Transactional
    public void sendResult(Model model, Task task) {
        var result = new Result();
        result.setTask(task);
        result.setModel(model);
        try {
            result = resultRepo.saveAndFlush(result);
            log.info("RESULT WITH ID={} TO TASK WITH ID={} WAS SENT", result.getId(), task.getId());
        } catch (DataIntegrityViolationException | PersistenceException exception) {
            log.error("ERROR WHEN SENDING RESULT FOR TASK WITH ID={}: {}",
                    task.getId(), exception.getMessage());
            throw new ResultCreationException("Error when sending result! [DatabaseException]", exception);
        }
    }

    @Override
    @Transactional
    public Result update(Result result, Mark mark, Teacher teacher) {
        result.setMark(mark);
        teacher.addResult(result);
        try {
            result = resultRepo.saveAndFlush(result);
            log.info("RESULT WITH ID={} WAS UPDATED!", result.getId());
            return result;
        } catch (DataIntegrityViolationException | PersistenceException exception) {
            log.error("ERROR WHEN UPDATING TASK: {}", exception.getMessage());
            throw new ResultCreationException("Error when updating result! [DatabaseException]", exception);
        }
    }
}

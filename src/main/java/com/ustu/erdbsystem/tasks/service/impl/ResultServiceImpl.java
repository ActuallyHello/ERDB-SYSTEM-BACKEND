package com.ustu.erdbsystem.tasks.service.impl;

import com.ustu.erdbsystem.ermodels.store.models.Model;
import com.ustu.erdbsystem.persons.store.models.Person;
import com.ustu.erdbsystem.persons.store.models.Teacher;
import com.ustu.erdbsystem.tasks.api.dtos.ResultDTO;
import com.ustu.erdbsystem.tasks.api.mapper.ResultDTOMapper;
import com.ustu.erdbsystem.tasks.exception.service.ResultCreationException;
import com.ustu.erdbsystem.tasks.service.ResultService;
import com.ustu.erdbsystem.tasks.store.models.Result;
import com.ustu.erdbsystem.tasks.store.models.Task;
import com.ustu.erdbsystem.tasks.store.repos.ResultRepo;
import jakarta.persistence.PersistenceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
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
    public Long create(ResultDTO resultDTO, Model model, Task task) {
        var result = ResultDTOMapper.fromDTO(resultDTO);
        result.setModel(model);
        result.setTask(task);
        try {
            result = resultRepo.saveAndFlush(result);
            log.info("CREATED RESULT WITH ID={}", result.getId());
            return result.getId();
        } catch (DataIntegrityViolationException | PersistenceException exception) {
            log.error("ERROR WHEN CREATING RESULT FOR TASK WITH ID={}: {}",
                    task.getId(), exception.getMessage());
            throw new ResultCreationException("Error when creating result! [DatabaseException]", exception);
        }
    }

    @Override
    @Transactional
    public Result update(Result newResult) {
        try {
            newResult = resultRepo.saveAndFlush(newResult);
            log.info("RESULT WITH ID={} WAS UPDATED!", newResult.getId());
            return newResult;
        } catch (DataIntegrityViolationException | PersistenceException exception) {
            log.error("ERROR WHEN UPDATING TASK: {}", exception.getMessage());
            throw new ResultCreationException("Error when updating result! [DatabaseException]", exception);
        }
    }
}

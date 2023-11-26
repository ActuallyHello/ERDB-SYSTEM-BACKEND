package com.ustu.erdbsystem.tasks.service.impl;

import com.ustu.erdbsystem.persons.store.models.Teacher;
import com.ustu.erdbsystem.tasks.api.dtos.TaskDTO;
import com.ustu.erdbsystem.tasks.api.mapper.TaskDTOMapper;
import com.ustu.erdbsystem.tasks.exception.service.TaskCreationException;
import com.ustu.erdbsystem.tasks.service.TaskService;
import com.ustu.erdbsystem.tasks.store.models.DenormalizeModel;
import com.ustu.erdbsystem.tasks.store.models.Task;
import com.ustu.erdbsystem.tasks.store.repos.TaskRepo;
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
public class TaskServiceImpl implements TaskService {

    private final TaskRepo taskRepo;

    @Override
    public List<Task> getAllWithTeachers() {
        var tasks = taskRepo.findAllWithTeachers();
        log.info("GET TASKS ({})", tasks.size());
        return tasks;
    }

    @Override
    public Optional<Task> getByIdWithDenormalizeModel(Long id) {
        var task = taskRepo.findByIdWithDenormalizeModel(id);
        log.info("GET TASK WITH ID={}", id);
        return task;
    }

    @Override
    @Transactional
    public Long create(TaskDTO taskDTO, Teacher teacher, List<DenormalizeModel> denormalizeModelList) {
        var task = TaskDTOMapper.fromDTO(taskDTO);
        denormalizeModelList.forEach(task::addDenormalizeModel);
        teacher.addTask(task);
        try {
            task = taskRepo.saveAndFlush(task);
            log.info("CREATED TASK WITH ID={}", task.getId());
            return task.getId();
        } catch (DataIntegrityViolationException | PersistenceException exception) {
            log.error("ERROR WHEN CREATING TASK: {}", exception.getMessage());
            throw new TaskCreationException("Error when creating task! [DatabaseException]", exception);
        }
    }
}

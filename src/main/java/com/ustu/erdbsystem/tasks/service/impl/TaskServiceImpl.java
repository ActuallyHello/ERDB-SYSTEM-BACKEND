package com.ustu.erdbsystem.tasks.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ustu.erdbsystem.external.TestDataLoader;
import com.ustu.erdbsystem.persons.store.models.Teacher;
import com.ustu.erdbsystem.tasks.api.dtos.TaskDTO;
import com.ustu.erdbsystem.tasks.api.dtos.TestDataDTO;
import com.ustu.erdbsystem.tasks.api.mapper.TaskDTOMapper;
import com.ustu.erdbsystem.tasks.exception.service.ConvertEntityToJsonException;
import com.ustu.erdbsystem.tasks.exception.service.TaskCreationException;
import com.ustu.erdbsystem.tasks.exception.service.TaskDeleteException;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {

    private final TaskRepo taskRepo;
    private final ObjectMapper objectMapper;
    private final TestDataLoader testDataLoader;

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

    @Override
    public TestDataDTO generateDataForTask(Task task) {
        TestDataDTO testData = new TestDataDTO();
        for (var denormalizeModel : task.getDenormalizeModelList()) {
            var mapView = getMapFromJsonView(denormalizeModel.getView());
            List<String> entities = new ArrayList<>();
            List<String> attributes = new ArrayList<>();
            switch (task.getComplexity()) {
                case EASY -> {
                    mapView.forEach((key, value) -> {
                        entities.add(key);
                        attributes.addAll(value.stream().map(attributeMap -> attributeMap.get("title")).toList());
                    });
                }
                case NORMAL -> {
                    mapView.values().forEach(value -> attributes.addAll(value.stream().map(attributeMap -> attributeMap.get("title")).toList()));
                }
                case DIFFICULT -> {
                    mapView.values().forEach(value -> attributes.addAll(
                            value.stream()
                                    .filter(attributeMap -> !attributeMap.get("type").equalsIgnoreCase("PK"))
                                    .map(attributeMap -> attributeMap.get("title"))
                                    .toList()));
                }
            }
            var data = testDataLoader.loadData(denormalizeModel.getModel().getTitle(), attributes, task.getTestDataAmount());
            testData.getEntities().addAll(entities);
            testData.getAttributes().addAll(attributes);
            testData.getTestData().addAll(data);
        }
        return testData;
    }

    @Override
    public void deleteTask(Task task) {
        try {
            taskRepo.delete(task);
            taskRepo.flush();
            log.info("TASK WITH ID={} WAS DELETED!", task.getId());
        } catch (DataIntegrityViolationException | PersistenceException exception) {
            log.error("ERROR WHEN DELETING TASK WITH ID={}: {}", task.getId(), exception.getMessage());
            throw new TaskDeleteException("Error when deleting task! [DatabaseException]", exception);
        }
    }

    private Map<String, List<Map<String, String>>> getMapFromJsonView(String jsonView) {
        try {
            return objectMapper.readValue(jsonView, new TypeReference<>(){});
        } catch (JsonProcessingException exception) {
            log.error("ERROR WHEN PARSING JSON FIELD FROM MODEL: {}", exception.getMessage());
            throw new ConvertEntityToJsonException("Error when parsing json field!", exception);
        }
    }
}

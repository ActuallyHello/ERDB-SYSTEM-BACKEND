package com.ustu.erdbsystem.tasks.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ustu.erdbsystem.ermodels.exception.response.ModelNotFoundException;
import com.ustu.erdbsystem.ermodels.service.ModelEntityAttributeService;
import com.ustu.erdbsystem.ermodels.service.ModelService;
import com.ustu.erdbsystem.ermodels.store.models.Model;
import com.ustu.erdbsystem.external.TestDataLoader;
import com.ustu.erdbsystem.external.exception.LoadTestDataException;
import com.ustu.erdbsystem.persons.api.mapper.PersonDTOMapper;
import com.ustu.erdbsystem.persons.api.mapper.PositionDTOMapper;
import com.ustu.erdbsystem.persons.api.mapper.TeacherDTOMapper;
import com.ustu.erdbsystem.persons.store.models.Student;
import com.ustu.erdbsystem.persons.store.models.Teacher;
import com.ustu.erdbsystem.tasks.api.dtos.TaskDTO;
import com.ustu.erdbsystem.tasks.api.dtos.TestDataDTO;
import com.ustu.erdbsystem.tasks.api.dtos.response.TaskWithTeacherDTO;
import com.ustu.erdbsystem.tasks.api.mapper.TaskDTOMapper;
import com.ustu.erdbsystem.tasks.api.mapper.TaskWithTeacherDTOMapper;
import com.ustu.erdbsystem.tasks.exception.response.DenormalizeModelServerException;
import com.ustu.erdbsystem.tasks.exception.response.ResultServerException;
import com.ustu.erdbsystem.tasks.exception.service.ConvertEntityToJsonException;
import com.ustu.erdbsystem.tasks.exception.service.DenormalizeModelCreationException;
import com.ustu.erdbsystem.tasks.exception.service.DenormalizeModelReceiveException;
import com.ustu.erdbsystem.tasks.exception.service.TaskCreationException;
import com.ustu.erdbsystem.tasks.exception.service.TaskDeleteException;
import com.ustu.erdbsystem.tasks.service.DenormalizeModelService;
import com.ustu.erdbsystem.tasks.service.TaskService;
import com.ustu.erdbsystem.tasks.store.models.DenormalizeModel;
import com.ustu.erdbsystem.tasks.store.models.Task;
import com.ustu.erdbsystem.tasks.store.repos.TaskRepo;
import com.ustu.erdbsystem.tasks.store.repos.TaskStudentRepo;
import jakarta.persistence.PersistenceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

    private final DenormalizeModelService denormalizeModelService;
    private final ModelEntityAttributeService modelEntityAttributeService;

    @Override
    public Optional<Task> getById(Long id) {
        var task = taskRepo.findById(id);
        log.debug("GET TASK WITH ID={}", id);
        return task;
    }

    @Override
    public List<Task> getAllWithTeachers() {
        var taskWithTeacherList = taskRepo.findAllWithTeachers();
        log.debug("GET TASKS ({})", taskWithTeacherList.size());
        return taskWithTeacherList;
    }

    @Override
    public List<TaskWithTeacherDTO> getAllTasksWithTeachersDTOList(Integer page, Integer size) {
        page = page == null ? 0 : page;
        size = size == null ? 20 : size;
        var pageable = PageRequest.of(page, size, Sort.by("id").descending());
        var taskWithTeacherList = taskRepo.findAllWithTeachers(pageable);
        log.debug("GET TASKS ({})", taskWithTeacherList.size());
        return taskWithTeacherList.stream()
                .map(task -> {
                    var teacher = task.getTeacher();
                    var person = teacher.getPerson();
                    var position = teacher.getPosition();
                    return TaskWithTeacherDTOMapper.makeDTO(
                            task,
                            TeacherDTOMapper.makeDTO(
                                    teacher,
                                    PersonDTOMapper.makeDTO(person),
                                    PositionDTOMapper.makeDTO(position)));
                })
                .toList();
    }

    @Override
    public Optional<Task> getByIdWithDenormalizeModel(Long id) {
        var task = taskRepo.findByIdWithDenormalizeModel(id);
        log.debug("GET TASK WITH ID={}", id);
        return task;
    }

    @Override
    public Optional<Task> getByIdWithTaskStudentList(Long id) {
        var task = taskRepo.findByIdWithTaskStudentList(id);
        log.debug("GET TASK WITH ID={}", id);
        return task;
    }

    @Override
    @Transactional
    public Long create(TaskDTO taskDTO, Teacher teacher, List<Model> modelList) {
        var task = TaskDTOMapper.fromDTO(taskDTO);
        for (var model : modelList) {
            var denormalizeModel = denormalizeModelService.getByModelWithTasks(model).orElseGet(() -> {
                model.setModelEntityList(modelEntityAttributeService.getAllByModel(model));
                return denormalizeModelService.create(model);
            });
            task.addDenormalizeModel(denormalizeModel);
        }
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
            List<List<String>> data = testDataLoader.loadData(denormalizeModel.getModel().getTitle(),
                    attributes, task.getTestDataAmount());
            testData.getEntities().addAll(entities);
            testData.getAttributes().addAll(attributes);
            testData.getTestData().addAll(data);
        }
        return testData;
    }

    @Override
    @Transactional
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

    @Override
    public Optional<Task> getByIdWithResults(Long id) {
        var task = taskRepo.findByIdWithResults(id);
        log.debug("GET TASK WITH ID={}", id);
        return task;
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

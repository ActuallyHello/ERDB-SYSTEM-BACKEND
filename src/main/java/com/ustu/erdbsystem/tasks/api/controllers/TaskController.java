package com.ustu.erdbsystem.tasks.api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ustu.erdbsystem.ermodels.exception.response.ModelNotFoundException;
import com.ustu.erdbsystem.ermodels.service.ModelEntityAttributeService;
import com.ustu.erdbsystem.ermodels.service.ModelService;
import com.ustu.erdbsystem.ermodels.store.repos.ModelRepo;
import com.ustu.erdbsystem.persons.api.mapper.PersonDTOMapper;
import com.ustu.erdbsystem.persons.api.mapper.PositionDTOMapper;
import com.ustu.erdbsystem.persons.api.mapper.TeacherDTOMapper;
import com.ustu.erdbsystem.persons.exception.response.TeacherNotFoundException;
import com.ustu.erdbsystem.persons.service.TeacherService;
import com.ustu.erdbsystem.tasks.api.dtos.request.CreateTaskRequestDTO;
import com.ustu.erdbsystem.tasks.api.dtos.response.TaskWithDenormalizeModelDTO;
import com.ustu.erdbsystem.tasks.api.dtos.response.TaskWithTeacherDTO;
import com.ustu.erdbsystem.tasks.api.dtos.response.TaskWithTestDataDTO;
import com.ustu.erdbsystem.tasks.api.mapper.TaskDTOMapper;
import com.ustu.erdbsystem.tasks.api.mapper.TaskWithTeacherDTOMapper;
import com.ustu.erdbsystem.tasks.exception.response.TaskNotFoundException;
import com.ustu.erdbsystem.tasks.exception.response.TaskServerException;
import com.ustu.erdbsystem.tasks.exception.service.DenormalizeModelCreationException;
import com.ustu.erdbsystem.tasks.exception.service.TaskCreationException;
import com.ustu.erdbsystem.tasks.exception.service.TaskDeleteException;
import com.ustu.erdbsystem.tasks.exception.service.TaskDoesNotExistException;
import com.ustu.erdbsystem.tasks.service.DenormalizeModelService;
import com.ustu.erdbsystem.tasks.service.TaskService;
import com.ustu.erdbsystem.tasks.store.models.DenormalizeModel;
import com.ustu.erdbsystem.tasks.store.models.Task;
import com.ustu.erdbsystem.tasks.store.repos.DenormalizeModelRepo;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final DenormalizeModelService denormalizeModelService;
    private final TeacherService teacherService;
    private final ModelService modelService;
    private final ModelEntityAttributeService modelEntityAttributeService;

    @GetMapping
    public ResponseEntity<List<TaskWithTeacherDTO>> getAllTasksWithTeachers() {
        var taskWithTeacherDTOList = taskService.getAllWithTeachers().stream()
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
        return ResponseEntity.ok(taskWithTeacherDTOList);
    }
    @GetMapping("/{id}")
    public ResponseEntity<TaskWithTestDataDTO> getTaskWithDenormalizeModelById(@PathVariable Long id) {
        var task = taskService.getByIdWithDenormalizeModel(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id=%d was not found!".formatted(id)));
        var testDataDTO = taskService.generateDataForTask(task);
        var taskWithTestDataDTO = TaskWithTestDataDTO.builder()
                .taskDTO(TaskDTOMapper.makeDTO(task))
                .testDataDTO(testDataDTO)
                .build();
        return ResponseEntity.ok(taskWithTestDataDTO);
    }

    @PostMapping
    public ResponseEntity<Object> createTask(@RequestBody @Valid CreateTaskRequestDTO createTaskRequestDTO) {
        var taskDTO = TaskDTOMapper.makeDTO(createTaskRequestDTO);
        var teacher = teacherService.getByIdWithTasks(createTaskRequestDTO.getTeacherId())
                .orElseThrow(() -> new TeacherNotFoundException("Teacher with id=%d was not found!".formatted(createTaskRequestDTO.getTeacherId())));
        try {
            var denormalizeModelList = createTaskRequestDTO.getModelIds().stream()
                    .map(modelId -> {
                        var denormalizeModel = denormalizeModelService.getByModelIdWithTasks(modelId);
                        if (denormalizeModel.isEmpty()) {
                            var model = modelService.getById(modelId)
                                    .orElseThrow(() -> new ModelNotFoundException("Model with id=%d was not found!".formatted(modelId)));
                            model.setModelEntityList(modelEntityAttributeService.getAllByModel(model));
                            return denormalizeModelService.create(model);
                        }
                        return denormalizeModel.get();
                    }).toList();
            var taskId = taskService.create(taskDTO, teacher, denormalizeModelList);
            return ResponseEntity.ok(Map.of("taskId", taskId));
        } catch (DenormalizeModelCreationException | TaskCreationException exception) {
            throw new TaskServerException(exception.getMessage(), exception);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTaskById(@PathVariable Long id) {
        var task = taskService.getByIdWithDenormalizeModel(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id=%d was not found!".formatted(id)));
        try {
            taskService.deleteTask(task);
            return ResponseEntity.noContent().build();
        } catch (TaskDeleteException exception) {
            throw new TaskServerException(exception.getMessage(), exception);
        }
    }
}

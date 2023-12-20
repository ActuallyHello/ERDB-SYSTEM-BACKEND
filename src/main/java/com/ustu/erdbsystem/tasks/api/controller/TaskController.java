package com.ustu.erdbsystem.tasks.api.controller;

import com.ustu.erdbsystem.ermodels.exception.response.ModelNotFoundException;
import com.ustu.erdbsystem.ermodels.service.ModelService;
import com.ustu.erdbsystem.external.exception.LoadTestDataException;
import com.ustu.erdbsystem.persons.exception.response.StudentNotFoundException;
import com.ustu.erdbsystem.persons.exception.response.TeacherNotFoundException;
import com.ustu.erdbsystem.persons.service.StudentService;
import com.ustu.erdbsystem.persons.service.TeacherService;
import com.ustu.erdbsystem.persons.store.models.Student;
import com.ustu.erdbsystem.tasks.api.dtos.TestDataDTO;
import com.ustu.erdbsystem.tasks.api.dtos.request.CreateTaskRequestDTO;
import com.ustu.erdbsystem.tasks.api.dtos.request.SendTasksToStudentsDTO;
import com.ustu.erdbsystem.tasks.api.dtos.response.TaskWithResultDTO;
import com.ustu.erdbsystem.tasks.api.dtos.response.TaskWithTeacherDTO;
import com.ustu.erdbsystem.tasks.api.dtos.response.TaskWithTestDataDTO;
import com.ustu.erdbsystem.tasks.api.mapper.TaskDTOMapper;
import com.ustu.erdbsystem.tasks.api.mapper.TaskWithTestDataDTOMapper;
import com.ustu.erdbsystem.tasks.exception.response.TaskNotFoundException;
import com.ustu.erdbsystem.tasks.exception.response.TaskServerException;
import com.ustu.erdbsystem.tasks.exception.service.ConvertEntityToJsonException;
import com.ustu.erdbsystem.tasks.exception.service.DenormalizeModelCreationException;
import com.ustu.erdbsystem.tasks.exception.service.TaskCreationException;
import com.ustu.erdbsystem.tasks.exception.service.TaskDeleteException;
import com.ustu.erdbsystem.tasks.service.TaskService;
import com.ustu.erdbsystem.tasks.service.TaskStudentService;
import com.ustu.erdbsystem.tasks.store.models.Task;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskStudentService taskStudentService;
    private final TeacherService teacherService;
    private final ModelService modelService;
    private final StudentService studentService;

    private static final String BY_ID = "/{id}";

    @GetMapping
    public ResponseEntity<List<TaskWithTeacherDTO>> getAllTasksWithTeachers(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        var taskWithTeacherDTOList = taskService.getAllTasksWithTeachersDTOList(page, size);
        return ResponseEntity.ok(taskWithTeacherDTOList);
    }
    @GetMapping(BY_ID)
    public ResponseEntity<TaskWithTestDataDTO> getTaskWithDenormalizeModelById(@PathVariable Long id) {
        var task = taskService.getByIdWithDenormalizeModel(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id=%d was not found!".formatted(id)));
        TestDataDTO testDataDTO;
        try {
            testDataDTO = taskService.generateDataForTask(task);
        } catch (ConvertEntityToJsonException | LoadTestDataException exception) {
            throw new TaskServerException(exception.getMessage(), exception);
        }
        var taskWithTestDataDTO = TaskWithTestDataDTOMapper.makeDTO(task, testDataDTO);
        return ResponseEntity.ok(taskWithTestDataDTO);
    }

    @GetMapping("/students/{studentId}")
    public ResponseEntity<List<TaskWithResultDTO>> getAllTasksForStudent(@PathVariable Long studentId) {
        var student = studentService.getByIdWithPersonAndGroup(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student with id=%d was not found!".formatted(studentId)));
        var taskWithResultDTOList = taskStudentService.getTasksWithResultsDTOByStudent(student);
        return ResponseEntity.ok(taskWithResultDTOList);
    }

    @PostMapping("/send")
    public ResponseEntity<Object> sendTasksToStudents(@RequestBody @Valid SendTasksToStudentsDTO sendTasksToStudentsDTO) {
        var taskList = sendTasksToStudentsDTO.getTaskIds().stream()
                .map(taskId -> taskService.getByIdWithTaskStudentList(taskId)
                        .orElseThrow(() -> new TaskNotFoundException(
                                "Task with id=%d was not found!".formatted(taskId))))
                .toList();
        var studentList = sendTasksToStudentsDTO.getStudentIds().stream()
                .map(studentId -> studentService.getByIdWithTaskStudentList(studentId)
                            .orElseThrow(() -> new StudentNotFoundException(
                                    "Student with id=%d was not found!".formatted(studentId))))
                .toList();
        taskStudentService.sendTasksToStudents(taskList, studentList);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping
    public ResponseEntity<Object> createTask(@RequestBody @Valid CreateTaskRequestDTO createTaskRequestDTO) {
        var taskDTO = TaskDTOMapper.makeDTO(createTaskRequestDTO);
        var teacher = teacherService.getByIdWithTasks(createTaskRequestDTO.getTeacherId())
                .orElseThrow(() -> new TeacherNotFoundException("Teacher with id=%d was not found!".formatted(createTaskRequestDTO.getTeacherId())));
        var modelList = createTaskRequestDTO.getModelIds().stream()
                .map(modelId -> modelService.getById(modelId)
                        .orElseThrow(() -> new ModelNotFoundException(
                                "Model with id=%d was not found!".formatted(modelId))))
                .toList();
        try {
            var taskId = taskService.create(taskDTO, teacher, modelList);
            return ResponseEntity.ok(Map.of("taskId", taskId));
        } catch (DenormalizeModelCreationException | TaskCreationException exception) {
            throw new TaskServerException(exception.getMessage(), exception);
        }
    }

    @DeleteMapping(BY_ID)
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

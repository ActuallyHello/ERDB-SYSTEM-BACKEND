package com.ustu.erdbsystem.tasks.api.controllers;

import com.ustu.erdbsystem.persons.api.mapper.PersonDTOMapper;
import com.ustu.erdbsystem.persons.api.mapper.PositionDTOMapper;
import com.ustu.erdbsystem.persons.api.mapper.TeacherDTOMapper;
import com.ustu.erdbsystem.persons.exception.response.StudentNotFoundException;
import com.ustu.erdbsystem.persons.service.StudentService;
import com.ustu.erdbsystem.persons.store.models.Student;
import com.ustu.erdbsystem.tasks.api.dtos.request.SendTasksToStudentsDTO;
import com.ustu.erdbsystem.tasks.api.dtos.response.TaskWithResultDTO;
import com.ustu.erdbsystem.tasks.api.mapper.ResultDTOMapper;
import com.ustu.erdbsystem.tasks.api.mapper.TaskWithResultDTOMapper;
import com.ustu.erdbsystem.tasks.exception.response.TaskNotFoundException;
import com.ustu.erdbsystem.tasks.service.ResultService;
import com.ustu.erdbsystem.tasks.service.TaskService;
import com.ustu.erdbsystem.tasks.service.TaskStudentService;
import com.ustu.erdbsystem.tasks.store.models.Task;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/tasks-students")
public class TaskStudentController {

    private final TaskService taskService;
    private final StudentService studentService;
    private final ResultService resultService;
    private final TaskStudentService taskStudentService;

    @GetMapping("/{studentId}")
    public ResponseEntity<List<TaskWithResultDTO>> getAllTasksByStudentId(@PathVariable Long studentId) {
        var student = studentService.getByIdWithPersonAndGroup(studentId)
                .orElseThrow(() -> new StudentNotFoundException("Student with id=%d was not found!".formatted(studentId)));
        var taskList = taskStudentService.getTasksWithTeachersAndResultsByStudent(student);
        var taskWithResultDTOList = taskList.stream()
                .map(task -> {
                    var teacher = task.getTeacher();
                    var teacherDTO = TeacherDTOMapper.makeDTO(
                            teacher,
                            PersonDTOMapper.makeDTO(teacher.getPerson()),
                            PositionDTOMapper.makeDTO(teacher.getPosition()));
                    var result = resultService.getLastByPersonAndTask(student.getPerson(), task);
                    var resultDTO = result.map(ResultDTOMapper::makeDTO).orElse(null);
                    return TaskWithResultDTOMapper.makeDTO(task, teacherDTO, resultDTO);
                })
                .toList();
        return ResponseEntity.ok(taskWithResultDTOList);
    }

    @PostMapping
    public ResponseEntity<Object> sendTasksToStudents(@RequestBody @Valid SendTasksToStudentsDTO sendTasksToStudentsDTO) {
        List<Task> taskList = new ArrayList<>();
        for (var taskId : sendTasksToStudentsDTO.getTaskIds()) {
            var task = taskService.getByIdWithTaskStudentList(taskId)
                    .orElseThrow(() -> new TaskNotFoundException("Task with id=%d was not found!".formatted(taskId)));
            taskList.add(task);
        }
        List<Student> studentList = new ArrayList<>();
        for (var studentId : sendTasksToStudentsDTO.getStudentIds()) {
            var student = studentService.getByIdWithPersonAndGroup(studentId)
                    .orElseThrow(() -> new StudentNotFoundException("Student with id=%d was not found!".formatted(studentId)));
            studentList.add(student);
        }
        taskStudentService.sendTasksToStudents(taskList, studentList);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}

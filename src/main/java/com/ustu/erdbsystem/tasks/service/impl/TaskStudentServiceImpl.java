package com.ustu.erdbsystem.tasks.service.impl;

import com.ustu.erdbsystem.persons.api.mapper.PersonDTOMapper;
import com.ustu.erdbsystem.persons.api.mapper.PositionDTOMapper;
import com.ustu.erdbsystem.persons.api.mapper.TeacherDTOMapper;
import com.ustu.erdbsystem.persons.store.models.Student;
import com.ustu.erdbsystem.tasks.api.dtos.response.TaskWithResultDTO;
import com.ustu.erdbsystem.tasks.api.mapper.ResultDTOMapper;
import com.ustu.erdbsystem.tasks.api.mapper.TaskWithResultDTOMapper;
import com.ustu.erdbsystem.tasks.exception.service.TaskStudentCreationException;
import com.ustu.erdbsystem.tasks.service.ResultService;
import com.ustu.erdbsystem.tasks.service.TaskStudentService;
import com.ustu.erdbsystem.tasks.store.models.Task;
import com.ustu.erdbsystem.tasks.store.models.TaskStudent;
import com.ustu.erdbsystem.tasks.store.repos.TaskStudentRepo;
import jakarta.persistence.PersistenceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class TaskStudentServiceImpl implements TaskStudentService {

    private final TaskStudentRepo taskStudentRepo;
    private final ResultService resultService;

    @Override
    public List<Task> getTasksWithTeachersAndResultsByStudent(Student student) {
        var taskList = taskStudentRepo.findAllTasksWithTeachersAndResultsByStudent(student);
        log.debug("GET TASKS ({})", taskList.size());
        return taskList;
    }

    @Override
    public List<TaskWithResultDTO> getTasksWithResultsDTOByStudent(Student student) {
        var taskList = getTasksWithTeachersAndResultsByStudent(student);
        return taskList.stream()
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
    }

    @Override
    @Transactional
    public void sendTasksToStudents(List<Task> taskList, List<Student> studentList) {
        List<TaskStudent> taskStudentList = new ArrayList<>();
        for (var task : taskList) {
            for (var student : studentList) {
                var taskStudent = new TaskStudent();
                taskStudent.setStudent(student);
                taskStudent.setTask(task);
                taskStudentList.add(taskStudent);
            }
        }
        try {
            taskStudentRepo.saveAllAndFlush(taskStudentList);
            log.info("SENT {} TASK(S) TO {} STUDENT(S)!", taskList.size(), studentList.size());
        } catch (DataIntegrityViolationException | PersistenceException exception) {
            log.error("ERROR WHEN CREATING TASK STUDENT TO SEND {} TASK(S) TO {} STUDENT(S): {}",
                    taskList.size(), studentList.size(), exception.getMessage());
            throw new TaskStudentCreationException("Error when sending tasks for students! [DatabaseException]", exception);
        }
    }
}

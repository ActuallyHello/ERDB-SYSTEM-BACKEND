package com.ustu.erdbsystem.tasks.service;

import com.ustu.erdbsystem.persons.store.models.Student;
import com.ustu.erdbsystem.tasks.api.dtos.response.TaskWithResultDTO;
import com.ustu.erdbsystem.tasks.store.models.Task;

import java.util.List;

public interface TaskStudentService {

    List<Task> getTasksWithTeachersAndResultsByStudent(Student student);

    List<TaskWithResultDTO> getTasksWithResultsDTOByStudent(Student student);

    void sendTasksToStudents(List<Task> taskList, List<Student> studentList);
}

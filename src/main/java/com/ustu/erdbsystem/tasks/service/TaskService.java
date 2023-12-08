package com.ustu.erdbsystem.tasks.service;

import com.ustu.erdbsystem.persons.store.models.Student;
import com.ustu.erdbsystem.persons.store.models.Teacher;
import com.ustu.erdbsystem.tasks.api.dtos.TaskDTO;
import com.ustu.erdbsystem.tasks.api.dtos.TestDataDTO;
import com.ustu.erdbsystem.tasks.store.models.DenormalizeModel;
import com.ustu.erdbsystem.tasks.store.models.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    Optional<Task> getById(Long id);

    List<Task> getAllWithTeachers();

    Optional<Task> getByIdWithDenormalizeModel(Long id);

    Optional<Task> getByIdWithTaskStudentList(Long id);

    Long create(TaskDTO taskDTO, Teacher teacher, List<DenormalizeModel> denormalizeModelList);

    TestDataDTO generateDataForTask(Task task);

    void deleteTask(Task task);
}

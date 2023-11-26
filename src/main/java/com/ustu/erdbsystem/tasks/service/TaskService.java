package com.ustu.erdbsystem.tasks.service;

import com.ustu.erdbsystem.ermodels.store.models.Model;
import com.ustu.erdbsystem.persons.store.models.Teacher;
import com.ustu.erdbsystem.tasks.api.dtos.TaskDTO;
import com.ustu.erdbsystem.tasks.store.models.DenormalizeModel;
import com.ustu.erdbsystem.tasks.store.models.Task;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    List<Task> getAllWithTeachers();

    Optional<Task> getByIdWithDenormalizeModel(Long id);

    Long create(TaskDTO taskDTO, Teacher teacher, List<DenormalizeModel> denormalizeModelList);
}

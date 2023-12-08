package com.ustu.erdbsystem.tasks.service;


import com.ustu.erdbsystem.ermodels.store.models.Model;
import com.ustu.erdbsystem.persons.store.models.Person;
import com.ustu.erdbsystem.persons.store.models.Teacher;
import com.ustu.erdbsystem.tasks.api.dtos.ResultDTO;
import com.ustu.erdbsystem.tasks.store.models.Result;
import com.ustu.erdbsystem.tasks.store.models.Task;

import java.util.Optional;

public interface ResultService {

    Optional<Result> getById(Long id);

    Optional<Result> getByIdWithModelAndTaskAndTeacher(Long id);

    Optional<Result> getLastByPersonAndTask(Person person, Task task);

    Long create(ResultDTO resultDTO, Model model, Task task);

    Result update(Result newResult);
}

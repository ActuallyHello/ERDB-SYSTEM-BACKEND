package com.ustu.erdbsystem.tasks.service;


import com.ustu.erdbsystem.ermodels.store.models.Model;
import com.ustu.erdbsystem.persons.store.models.Person;
import com.ustu.erdbsystem.persons.store.models.Teacher;
import com.ustu.erdbsystem.tasks.api.dtos.ResultDTO;
import com.ustu.erdbsystem.tasks.api.dtos.response.ResultWithModelDTO;
import com.ustu.erdbsystem.tasks.api.dtos.response.ResultWithTaskDTO;
import com.ustu.erdbsystem.tasks.store.models.Result;
import com.ustu.erdbsystem.tasks.store.models.Task;
import com.ustu.erdbsystem.tasks.store.models.enums.Mark;

import java.util.List;
import java.util.Optional;

public interface ResultService {

    List<Result> getAllWithTaskAndModelAndTeacher(Integer page, Integer size);

    List<ResultWithTaskDTO> getAllPreview(Integer page, Integer size);

    Optional<Result> getById(Long id);

    ResultWithModelDTO getByIdToEvaluateResult(Result result);

    Optional<Result> getByIdWithModelAndTaskAndTeacher(Long id);

    Optional<Result> getLastByPersonAndTask(Person person, Task task);

    void sendResult(Model model, Task task);

    Result update(Result result, Mark mark, Teacher teacher);
}

package com.ustu.erdbsystem.tasks.api.controllers;

import com.ustu.erdbsystem.ermodels.api.dto.response.ModelDetailDTO;
import com.ustu.erdbsystem.ermodels.exception.response.ModelNotFoundException;
import com.ustu.erdbsystem.ermodels.service.ModelService;
import com.ustu.erdbsystem.persons.api.dto.TeacherDTO;
import com.ustu.erdbsystem.persons.api.mapper.PersonDTOMapper;
import com.ustu.erdbsystem.persons.api.mapper.PositionDTOMapper;
import com.ustu.erdbsystem.persons.api.mapper.TeacherDTOMapper;
import com.ustu.erdbsystem.persons.exception.response.TeacherNotFoundException;
import com.ustu.erdbsystem.persons.service.TeacherService;
import com.ustu.erdbsystem.tasks.api.dtos.ResultDTO;
import com.ustu.erdbsystem.tasks.api.dtos.request.CreateResultRequestDTO;
import com.ustu.erdbsystem.tasks.api.dtos.request.UpdateResultRequestDTO;
import com.ustu.erdbsystem.tasks.api.dtos.response.ResultWithModelDTO;
import com.ustu.erdbsystem.tasks.api.dtos.response.TaskWithResultDTO;
import com.ustu.erdbsystem.tasks.api.mapper.ResultDTOMapper;
import com.ustu.erdbsystem.tasks.api.mapper.TaskTitleDTOMapper;
import com.ustu.erdbsystem.tasks.exception.response.ResultNotFoundException;
import com.ustu.erdbsystem.tasks.exception.response.ResultServerException;
import com.ustu.erdbsystem.tasks.exception.response.TaskNotFoundException;
import com.ustu.erdbsystem.tasks.exception.service.ResultCreationException;
import com.ustu.erdbsystem.tasks.service.ResultService;
import com.ustu.erdbsystem.tasks.service.TaskService;
import com.ustu.erdbsystem.tasks.store.models.enums.Mark;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/results")
public class ResultController {

    private final ResultService resultService;
    private final ModelService modelService;
    private final TaskService taskService;
    private final TeacherService teacherService;

    private final String HOST = "http://localhost:8080";
    private final String MODEL_API_ENDPOINT = "/models";
    private final String TEACHER_API_ENDPOINT = "/teachers";

    @GetMapping("/{id}")
    public ResponseEntity<ResultWithModelDTO> getResultById(@PathVariable Long id) {
        var result = resultService.getByIdWithModelAndTaskAndTeacher(id)
                .orElseThrow(() -> new ResultNotFoundException("Result with id=%d was not found".formatted(id)));
        var taskTitleDTO = TaskTitleDTOMapper.makeDTO(result.getTask());
//        var modelDetailDTO = restTemplate.getForEntity(HOST + MODEL_API_ENDPOINT + result.getModel().getId(), ModelDetailDTO.class);
        // TODO ....
        return null;
    }

    @PostMapping
    public ResponseEntity<Object> createResult(@RequestBody @Valid CreateResultRequestDTO createResultRequestDTO) {
        var model = modelService.getById(createResultRequestDTO.getModelId())
                .orElseThrow(() -> new ModelNotFoundException(
                        "Model with id=%d was not found".formatted(createResultRequestDTO.getModelId())));
        var task = taskService.getById(createResultRequestDTO.getTaskId())
                .orElseThrow(() -> new TaskNotFoundException(
                        "Task with id=%d was not found".formatted(createResultRequestDTO.getTaskId())));
        var resultDTO = ResultDTOMapper.makeDTO(createResultRequestDTO); // TODO EnumException change to ModelValidationException skip catch?
        try {
            var resultId = resultService.create(resultDTO, model, task);
            return new ResponseEntity<>(Map.of("resultId", resultId), HttpStatus.CREATED);
        } catch (ResultCreationException exception) {
            throw new ResultServerException(exception.getMessage(), exception);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ResultDTO> updateResultByTeacher(
            @RequestBody @Valid UpdateResultRequestDTO updateResultRequestDTO,
            @PathVariable Long id) {
        var result = resultService.getById(id)
                .orElseThrow(() -> new ResultNotFoundException("Result with id=%d was not found".formatted(id)));
        var teacher = teacherService.getById(updateResultRequestDTO.getTeacherId())
                .orElseThrow(() -> new TeacherNotFoundException(
                        "Teacher with id=%d was not found".formatted(updateResultRequestDTO.getTeacherId())));
        result.setMark(Mark.fromInteger(updateResultRequestDTO.getMark()));
        result.setTeacher(teacher);
        try {
            result = resultService.update(result);
            var resultDTO = ResultDTOMapper.makeDTO(result);
            return ResponseEntity.ok(resultDTO);
        } catch (ResultCreationException exception) {
            throw new ResultServerException(exception.getMessage(), exception);
        }
    }
}

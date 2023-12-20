package com.ustu.erdbsystem.ermodels.api.controller;

import com.ustu.erdbsystem.ermodels.api.dto.response.ModelDetailDTO;
import com.ustu.erdbsystem.ermodels.api.dto.request.CreateModelRequestDTO;
import com.ustu.erdbsystem.ermodels.api.dto.response.ModelWithPersonDTO;
import com.ustu.erdbsystem.ermodels.api.mapper.AttributeDTOMapper;
import com.ustu.erdbsystem.ermodels.api.mapper.ModelDTOMapper;
import com.ustu.erdbsystem.ermodels.api.mapper.ModelEntityDTOMapper;
import com.ustu.erdbsystem.ermodels.api.mapper.ModelWithPersonDTOMapper;
import com.ustu.erdbsystem.ermodels.api.mapper.RelationDTOMapper;
import com.ustu.erdbsystem.ermodels.exception.response.ModelServerException;
import com.ustu.erdbsystem.ermodels.exception.response.ModelNotFoundException;
import com.ustu.erdbsystem.ermodels.exception.service.ModelCreationException;
import com.ustu.erdbsystem.ermodels.exception.service.ModelDeleteException;
import com.ustu.erdbsystem.ermodels.service.ModelService;
import com.ustu.erdbsystem.persons.api.mapper.PersonDTOMapper;
import com.ustu.erdbsystem.persons.exception.response.PersonNotFoundException;
import com.ustu.erdbsystem.persons.service.PersonService;
import com.ustu.erdbsystem.tasks.exception.response.TaskNotFoundException;
import com.ustu.erdbsystem.tasks.exception.service.ResultCreationException;
import com.ustu.erdbsystem.tasks.service.ResultService;
import com.ustu.erdbsystem.tasks.service.TaskService;
import com.ustu.erdbsystem.tasks.store.models.Task;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/models")
public class ModelController {

    private final ModelService modelService;
    private final PersonService personService;
    private final ResultService resultService;
    private final TaskService taskService;

    private final static String BY_ID = "/{id}";
    private final static String BY_PERSON_ID = "/persons/{personId}";

    @GetMapping
    public ResponseEntity<List<ModelWithPersonDTO>> getModelsWithAuthors(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) Boolean includeStudents,
            @RequestParam(required = false) Boolean includeTaskResults) {
        page = page == null ? 0 : page;
        size = size == null ? 20 : size;
        if (includeStudents == null) includeStudents = false;
        if (includeTaskResults == null) includeTaskResults = false;
        var modelList = modelService.getAll(page, size, includeStudents, includeTaskResults);
        var modelWithPersonDTOList = modelList.stream()
                .map(model -> ModelWithPersonDTOMapper.makeDTO(
                        model,
                        PersonDTOMapper.makeDTO(model.getPerson()))
                ).toList();
        return ResponseEntity.ok(modelWithPersonDTOList);
    }

    @GetMapping(BY_ID)
    public ResponseEntity<ModelDetailDTO> getModelDetailById(@PathVariable Long id) {
        var modelDetailDTO = modelService.getById(id)
                .map(modelService::getModelDetailDTOByModel)
                .orElseThrow(() -> new ModelNotFoundException("Model with id=%d was not found!".formatted(id)));
        return ResponseEntity.ok(modelDetailDTO);
    }

    @GetMapping(BY_PERSON_ID)
    public ResponseEntity<List<ModelWithPersonDTO>> getModelsWithPersonByPersonId(@PathVariable Long personId) {
        var person = personService.getById(personId)
                .orElseThrow(() -> new PersonNotFoundException("Person with id=%d was not found".formatted(personId)));
        var modelList = modelService.getAllByPerson(person);
        var modelWithPersonDTOList = modelList.stream()
                .map(model -> ModelWithPersonDTOMapper.makeDTO(model,
                        PersonDTOMapper.makeDTO(model.getPerson()))
                ).toList();
        return ResponseEntity.ok(modelWithPersonDTOList);
    }

    @PostMapping
    public ResponseEntity<Object> createModel(@RequestBody @Valid CreateModelRequestDTO createModelRequestDTO,
                                              @RequestParam(required = false) Long taskId) {
        var person = personService.getByIdWithModels(createModelRequestDTO.getPersonId())
                .orElseThrow(() -> new PersonNotFoundException(
                        "Person with id=%d was not found".formatted(createModelRequestDTO.getPersonId())));
        var modelDTO = ModelDTOMapper.makeDTO(createModelRequestDTO);
        var modelEntityDTOList = createModelRequestDTO.getTableList().stream()
                .map(tableRequestDTO -> {
                    var attributeDTOList = AttributeDTOMapper.makeDTO(tableRequestDTO);
                    return ModelEntityDTOMapper.makeDTO(tableRequestDTO, attributeDTOList);
                }).toList();
        var relationDTOList = createModelRequestDTO.getRelationList().stream()
                .map(RelationDTOMapper::makeDTO)
                .toList();
        var task = taskId != null
                ? taskService.getByIdWithResults(taskId)
                    .orElseThrow(() -> new TaskNotFoundException("Task with id=%d was not found".formatted(taskId)))
                : null;
        modelDTO.setIsTaskResult(task != null);
        try {
            var model = modelService.create(modelDTO, modelEntityDTOList, relationDTOList, person);
            if (task != null) {
                resultService.sendResult(model, task);
            }
            return new ResponseEntity<>(Map.of("modelId", model.getId()), HttpStatus.CREATED);
        } catch (ModelCreationException | ResultCreationException exception) {
            throw new ModelServerException(exception.getMessage(), exception);
        }
    }

    @DeleteMapping(BY_ID)
    public ResponseEntity<Object> deleteModel(@PathVariable Long id) {
        var model = modelService.getById(id)
                .orElseThrow(() -> new ModelNotFoundException("Model with id=%d was not found!".formatted(id)));
        try {
            modelService.deleteModel(model);
            return ResponseEntity.noContent().build();
        } catch (ModelDeleteException exception) {
            throw new ModelServerException(exception.getMessage(), exception);
        }
    }
}

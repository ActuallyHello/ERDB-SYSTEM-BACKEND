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
import com.ustu.erdbsystem.ermodels.exception.service.ModelEntityCreationException;
import com.ustu.erdbsystem.ermodels.exception.service.RelationCreationException;
import com.ustu.erdbsystem.ermodels.exception.service.RelationDeleteException;
import com.ustu.erdbsystem.ermodels.service.ModelService;
import com.ustu.erdbsystem.ermodels.service.facade.ModelEntityRelationFacade;
import com.ustu.erdbsystem.external.TestDataLoader;
import com.ustu.erdbsystem.external.exception.UploadTestDataException;
import com.ustu.erdbsystem.persons.api.mapper.PersonDTOMapper;
import com.ustu.erdbsystem.persons.exception.response.PersonNotFoundException;
import com.ustu.erdbsystem.persons.service.PersonService;
import com.ustu.erdbsystem.tasks.exception.response.TaskNotFoundException;
import com.ustu.erdbsystem.tasks.exception.service.ResultCreationException;
import com.ustu.erdbsystem.tasks.service.ResultService;
import com.ustu.erdbsystem.tasks.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
import org.springframework.web.multipart.MultipartFile;

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
    private final TestDataLoader testDataLoader;

    private final ModelEntityRelationFacade modelEntityRelationFacade;

    private final static String BY_ID = "/{id}";
    private final static String BY_PERSON_ID = "/persons/{personId}";
    private final static String UPLOAD_TEST_DATA_TO_MODEL = "/upload-test-data/{id}";

    @GetMapping
    public ResponseEntity<List<ModelWithPersonDTO>> getModelsWithAuthors(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false) Boolean includeStudents,
            @RequestParam(required = false) Boolean includeTaskResults) {
        page = page == null ? 0 : page;
        size = size == null ? 100 : size;
        var pageable = PageRequest.of(page, size, Sort.by("id", "title"));
        if (includeStudents == null) includeStudents = false;
        if (includeTaskResults == null) includeTaskResults = false;
        var modelList = modelService.getAll(pageable, includeStudents, includeTaskResults);
        var modelWithPersonDTOList = modelList.stream()
                .map(model -> ModelWithPersonDTOMapper.makeDTO(model, PersonDTOMapper.makeDTO(model.getPerson())))
                .toList();
        return ResponseEntity.ok(modelWithPersonDTOList);
    }

    @GetMapping(BY_ID)
    public ResponseEntity<ModelDetailDTO> getModelDetailById(@PathVariable Long id) {
        var modelDetailDTO = modelService.getById(id)
                .map(modelEntityRelationFacade::getModelDetailDTOByModel)
                .orElseThrow(() -> new ModelNotFoundException("Model with id=%d was not found!".formatted(id)));
        return ResponseEntity.ok(modelDetailDTO);
    }

    @GetMapping(BY_PERSON_ID)
    public ResponseEntity<List<ModelWithPersonDTO>> getModelsWithPersonByPersonId(@PathVariable Long personId) {
        var person = personService.getById(personId)
                .orElseThrow(() -> new PersonNotFoundException("Person with id=%d was not found".formatted(personId)));
        var modelList = modelService.getAllByPerson(person);
        var modelWithPersonDTOList = modelList.stream()
                .map(model -> ModelWithPersonDTOMapper.makeDTO(model, PersonDTOMapper.makeDTO(model.getPerson())))
                .toList();
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
            var model =
                    modelEntityRelationFacade.createModelWithRelatedEntities(modelDTO, person,
                            modelEntityDTOList, relationDTOList);
            if (task != null) resultService.sendResult(model, task);
            return new ResponseEntity<>(Map.of("modelId", model.getId()), HttpStatus.CREATED);
        } catch (ModelCreationException | ModelEntityCreationException |
                 RelationCreationException | ResultCreationException exception) {
            throw new ModelServerException(exception.getMessage(), exception);
        }
    }

    @DeleteMapping(BY_ID)
    public ResponseEntity<Object> deleteModel(@PathVariable Long id) {
        var model = modelService.getById(id)
                .orElseThrow(() -> new ModelNotFoundException("Model with id=%d was not found!".formatted(id)));
        try {
            modelEntityRelationFacade.deleteModelWithRelations(model);
            return ResponseEntity.noContent().build();
        } catch (ModelDeleteException | RelationDeleteException exception) {
            throw new ModelServerException(exception.getMessage(), exception);
        }
    }

    @PostMapping(UPLOAD_TEST_DATA_TO_MODEL)
    public ResponseEntity<Object> uploadTestDataToAModel(@PathVariable Long id,
                                                         @RequestParam("file") MultipartFile file) {
        var model = modelService.getById(id)
                .orElseThrow(() -> new ModelNotFoundException("Model with id=%d was not found!".formatted(id)));
        if (file.isEmpty()) {
            throw new ModelServerException("File is empty!");
        }
        try {
            testDataLoader.uploadTestDataFileToModel(model, file);
            return ResponseEntity.ok(Map.of("file", "uploaded"));
        } catch (UploadTestDataException exception) {
            throw new ModelServerException(exception.getMessage(), exception);
        }
    }
}

package com.ustu.erdbsystem.ermodels.api.controller;

import com.ustu.erdbsystem.ermodels.api.dto.ModelDTO;
import com.ustu.erdbsystem.ermodels.api.dto.ModelEntityDTO;
import com.ustu.erdbsystem.ermodels.api.dto.RelationDTO;
import com.ustu.erdbsystem.ermodels.api.dto.response.ModelDetailDTO;
import com.ustu.erdbsystem.ermodels.api.dto.request.CreateModelRequestDTO;
import com.ustu.erdbsystem.ermodels.api.dto.response.ModelWithPersonDTO;
import com.ustu.erdbsystem.ermodels.api.mapper.AttributeDTOMapper;
import com.ustu.erdbsystem.ermodels.api.mapper.ModelDTOMapper;
import com.ustu.erdbsystem.ermodels.api.mapper.ModelDetailDTOMapper;
import com.ustu.erdbsystem.ermodels.api.mapper.ModelEntityDTOMapper;
import com.ustu.erdbsystem.ermodels.api.mapper.ModelWithPersonDTOMapper;
import com.ustu.erdbsystem.ermodels.api.mapper.RelationDTOMapper;
import com.ustu.erdbsystem.ermodels.exception.response.ModelServerException;
import com.ustu.erdbsystem.ermodels.exception.response.ModelNotFoundException;
import com.ustu.erdbsystem.ermodels.exception.response.ModelOwnerNotFoundException;
import com.ustu.erdbsystem.ermodels.exception.response.ModelValidationException;
import com.ustu.erdbsystem.ermodels.exception.service.ModelCreationException;
import com.ustu.erdbsystem.ermodels.exception.service.ModelDeleteException;
import com.ustu.erdbsystem.ermodels.exception.validation.EnumValueException;
import com.ustu.erdbsystem.ermodels.service.ModelEntityAttributeService;
import com.ustu.erdbsystem.ermodels.service.ModelService;
import com.ustu.erdbsystem.ermodels.service.RelationService;
import com.ustu.erdbsystem.ermodels.store.models.Model;
import com.ustu.erdbsystem.persons.api.dto.PersonDTO;
import com.ustu.erdbsystem.persons.api.mapper.PersonDTOMapper;
import com.ustu.erdbsystem.persons.service.PersonService;
import com.ustu.erdbsystem.persons.store.models.Person;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
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
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/models")
public class ModelController {

    private final ModelService modelService;
    private final PersonService personService;

    private final static String BY_ID = "/{id}";
    private final static String BY_PERSON_ID = "/persons/{personId}";

    @GetMapping
    public ResponseEntity<List<ModelWithPersonDTO>> getModelsWithPerson(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        var modelWithPersonDTOList = modelService.getAllModelsWithPersonDTO(page, size);
        return ResponseEntity.ok(modelWithPersonDTOList);
    }

    @GetMapping(BY_PERSON_ID)
    public ResponseEntity<List<ModelWithPersonDTO>> getModelsWithPersonByPersonId(@PathVariable Long personId) {
        var person = personService.getByIdWithModels(personId)
                .orElseThrow(() -> new ModelOwnerNotFoundException("Person with id=%d was not found".formatted(personId)));
        var modelWithPersonDTOList = person.getModelList().stream()
                .map(model -> ModelWithPersonDTOMapper.makeDTO(model,
                        PersonDTOMapper.makeDTO(model.getPerson())))
                .toList();
        return ResponseEntity.ok(modelWithPersonDTOList);
    }

    @GetMapping(BY_ID)
    public ResponseEntity<ModelDetailDTO> getModelDetailById(@PathVariable Long id) {
        var model = modelService.getById(id)
                .orElseThrow(() -> new ModelNotFoundException("Model with id=%d was not found!".formatted(id)));
        var modelDetailDTO = modelService.getModelDetailDTOByModel(model);
        return ResponseEntity.ok(modelDetailDTO);
    }

    @PostMapping
    public ResponseEntity<Object> createModel(@RequestBody @Valid CreateModelRequestDTO createModelRequestDTO) {
        var person = personService.getByIdWithModels(createModelRequestDTO.getPersonId())
                .orElseThrow(() -> new ModelOwnerNotFoundException("Person with id=%d was not found".formatted(createModelRequestDTO.getPersonId())));
        var modelDTO = ModelDTOMapper.makeDTO(createModelRequestDTO);
        var modelEntityDTOList = createModelRequestDTO.getTableList().stream()
                    .map(tableRequestDTO -> {
                        var attributeDTOList = AttributeDTOMapper.makeDTO(tableRequestDTO);
                        return ModelEntityDTOMapper.makeDTO(tableRequestDTO, attributeDTOList);
                    })
                    .toList();
        var relationDTOList = createModelRequestDTO.getRelationList().stream()
                    .map(RelationDTOMapper::makeDTO)
                    .toList();
        try {
            var model = modelService.create(modelDTO, modelEntityDTOList, relationDTOList, person);
            return new ResponseEntity<>(Map.of("modelId", model.getId()), HttpStatus.CREATED);
        } catch (ModelCreationException exception) {
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

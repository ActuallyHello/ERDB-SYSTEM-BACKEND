package com.ustu.erdbsystem.ermodels.api.controller;

import com.ustu.erdbsystem.ermodels.api.dto.ModelDTO;
import com.ustu.erdbsystem.ermodels.api.dto.ModelEntityDTO;
import com.ustu.erdbsystem.ermodels.api.dto.RelationDTO;
import com.ustu.erdbsystem.ermodels.api.dto.response.ModelDetailDTO;
import com.ustu.erdbsystem.ermodels.api.dto.request.CreateModelRequestDTO;
import com.ustu.erdbsystem.ermodels.api.dto.response.ModelWithPersonDTO;
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
import com.ustu.erdbsystem.persons.api.mapper.PersonDTOMapper;
import com.ustu.erdbsystem.persons.service.PersonService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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

@AllArgsConstructor
@RestController
@RequestMapping("/models")
public class ModelController {

    private ModelService modelService;
    private PersonService personService;
    private RelationService relationService;
    private ModelEntityAttributeService modelEntityAttributeService;


    @GetMapping
    public ResponseEntity<List<ModelWithPersonDTO>> getModelsWithPerson(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        List<Model> modelWithPersonList;
        if (page == null && size == null) {
            modelWithPersonList = modelService.getAllWithPerson();
        } else {
            page = page == null ? 0 : page;
            size = size == null ? 20 : size;
            modelWithPersonList = modelService.getAllWithPerson(page, size);
        }
        var modelWithPersonDTOList = modelWithPersonList.stream()
                .map(model -> ModelWithPersonDTOMapper.makeDTO(
                        model,
                        PersonDTOMapper.makeDTO(model.getPerson())
                ))
                .toList();
        return ResponseEntity.ok(modelWithPersonDTOList);
    }

    @GetMapping("/persons/{id}")
    public ResponseEntity<List<ModelWithPersonDTO>> getModelsWithPersonByPersonId(@PathVariable Long id) {
        var person = personService.getByIdWithModels(id)
                .orElseThrow(() -> new ModelOwnerNotFoundException("Person with id=%d was not found".formatted(id)));
        var modelWithPersonDTOList = person.getModelList().stream()
                .map(model -> ModelWithPersonDTOMapper.makeDTO(
                        model,
                        PersonDTOMapper.makeDTO(model.getPerson())
                ))
                .toList();
        return ResponseEntity.ok(modelWithPersonDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModelDetailDTO> getModelDetailById(@PathVariable Long id) {
        var model = modelService.getById(id)
                .orElseThrow(() -> new ModelNotFoundException("Model with id=%d was not found!".formatted(id)));
        var personDTO = PersonDTOMapper.makeDTO(model.getPerson());
        var modelEntityDTOList = modelEntityAttributeService.getAllByModel(model).stream()
                .map(ModelEntityDTOMapper::makeDTO)
                .toList();
        var modelEntityIdList = modelEntityDTOList.stream()
                .map(ModelEntityDTO::getId)
                .toList();
        var relationDTOList = relationService.getRelationsByEntityIds(modelEntityIdList).stream()
                .map(RelationDTOMapper::makeDTO)
                .toList();
        return ResponseEntity.ok(
                ModelDetailDTOMapper.makeDTO(model, personDTO, modelEntityDTOList, relationDTOList)
        );
    }

    @PostMapping
    public ResponseEntity<Object> createModel(@RequestBody @Valid CreateModelRequestDTO createModelRequestDTO) {
        var person = personService.getByIdWithModels(createModelRequestDTO.getPersonId())
                .orElseThrow(() -> new ModelOwnerNotFoundException("Person with id=%d was not found".formatted(createModelRequestDTO.getPersonId())));
        ModelDTO modelDTO;
        List<ModelEntityDTO> modelEntityDTOList;
        List<RelationDTO> relationDTOList;
        try {
            modelDTO = ModelDTOMapper.makeDTO(createModelRequestDTO);
            modelEntityDTOList = createModelRequestDTO.getTableList().stream()
                    .map(ModelEntityDTOMapper::makeDTO)
                    .toList();
            relationDTOList = createModelRequestDTO.getRelationList().stream()
                    .map(RelationDTOMapper::makeDTO)
                    .toList();
        } catch (EnumValueException exception) {
            throw new ModelValidationException(exception.getMessage(), exception);
        }

        try {
            var model = modelService.create(person, modelDTO, modelEntityDTOList, relationDTOList);
            return ResponseEntity.ok(Map.of("modelId", model.getId()));
        } catch (ModelCreationException exception) {
            throw new ModelServerException(exception.getMessage(), exception);
        }
    }

    @DeleteMapping("/{id}")
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

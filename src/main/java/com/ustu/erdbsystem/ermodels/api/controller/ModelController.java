package com.ustu.erdbsystem.ermodels.api.controller;

import com.ustu.erdbsystem.ermodels.api.dto.ModelDTO;
import com.ustu.erdbsystem.ermodels.api.dto.ModelEntityDTO;
import com.ustu.erdbsystem.ermodels.api.dto.RelationDTO;
import com.ustu.erdbsystem.ermodels.api.dto.response.ModelDetailDTO;
import com.ustu.erdbsystem.ermodels.api.dto.response.ModelPreviewDTO;
import com.ustu.erdbsystem.ermodels.api.dto.request.CreateModelRequestDTO;
import com.ustu.erdbsystem.ermodels.api.mapper.ModelDTOMapper;
import com.ustu.erdbsystem.ermodels.api.mapper.ModelEntityDTOMapper;
import com.ustu.erdbsystem.ermodels.api.mapper.RelationDTOMapper;
import com.ustu.erdbsystem.ermodels.exception.response.ModelDBException;
import com.ustu.erdbsystem.ermodels.exception.response.ModelNotFoundException;
import com.ustu.erdbsystem.ermodels.exception.response.ModelOwnerNotFoundException;
import com.ustu.erdbsystem.ermodels.exception.response.ModelValidationException;
import com.ustu.erdbsystem.ermodels.exception.service.ModelCreationException;
import com.ustu.erdbsystem.ermodels.exception.service.ModelDeleteException;
import com.ustu.erdbsystem.ermodels.exception.validation.EnumValueException;
import com.ustu.erdbsystem.ermodels.service.ModelEntityAttributeService;
import com.ustu.erdbsystem.ermodels.service.ModelService;
import com.ustu.erdbsystem.ermodels.service.RelationService;
import com.ustu.erdbsystem.persons.api.mapper.PersonCredentialsDTOMapper;
import com.ustu.erdbsystem.persons.store.repos.PersonRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/models")
public class ModelController {

    private ModelService modelService;
    private PersonRepo personRepo; // TODO: change with service
    private RelationService relationService;
    private ModelEntityAttributeService modelEntityAttributeService;

    @GetMapping("")
    public ResponseEntity<List<ModelPreviewDTO>> getAllPreviewModels() {
        var result = modelService.getAll().stream()
                .map(model -> {
                    var personCredentialsDTO = PersonCredentialsDTOMapper.makeDTO(model.getPerson());
                    var modelDTO = ModelDTOMapper.makeDTO(model);
                    return ModelPreviewDTO.builder()
                            .personCredentialsDTO(personCredentialsDTO)
                            .modelDTO(modelDTO)
                            .build();
                })
                .toList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModelDetailDTO> getModelDetailById(@PathVariable Long id) {
        var model = modelService.getById(id)
                .orElseThrow(() -> new ModelNotFoundException("Model with id=%d was not found!".formatted(id)));
        var personCredentialsDTO = PersonCredentialsDTOMapper.makeDTO(model.getPerson());
        var modelDTO = ModelDTOMapper.makeDTO(model);
        var modelEntityDTOList = modelEntityAttributeService.getAllByModel(model).stream()
                .map(ModelEntityDTOMapper::makeDTO)
                .toList();
        var modelEntityIdList = modelEntityDTOList.stream()
                .map(ModelEntityDTO::getId)
                .toList();
        var relationDTOList = relationService.getRelationsByEntityIds(modelEntityIdList).stream()
                .map(RelationDTOMapper::makeDTO)
                .toList();
        return ResponseEntity.ok(ModelDetailDTO.builder()
                .modelDTO(modelDTO)
                .modelEntityDTOList(modelEntityDTOList)
                .relationDTOList(relationDTOList)
                .personCredentialsDTO(personCredentialsDTO)
                .build()
        );
    }

    @PostMapping
    public ResponseEntity<Long> createModel(@RequestBody CreateModelRequestDTO createModelRequestDTO) {
        var person = personRepo.findById(createModelRequestDTO.getPersonId())
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
        } catch (IllegalArgumentException | EnumValueException exception) {
            throw new ModelValidationException("Validation error! " + exception.getMessage(), exception);
        }

        try {
            var model = modelService.create(person, modelDTO, modelEntityDTOList, relationDTOList);
            return ResponseEntity.ok(model.getId());
        } catch (ModelCreationException exception) {
            throw new ModelDBException("Error when creating model! " + exception.getMessage(), exception);
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
            throw new ModelDBException("Error when deleting model! " + exception.getMessage(), exception);
        }
    }
}

package com.ustu.erdbsystem.ermodels.api.controller;

import com.ustu.erdbsystem.ermodels.api.dto.ModelEntityDTO;
import com.ustu.erdbsystem.ermodels.api.dto.response.ModelDetailDTO;
import com.ustu.erdbsystem.ermodels.api.dto.response.ModelPreviewDTO;
import com.ustu.erdbsystem.ermodels.api.dto.request.CreateModelRequestDTO;
import com.ustu.erdbsystem.ermodels.api.mapper.ModelDTOMapper;
import com.ustu.erdbsystem.ermodels.api.mapper.ModelEntityDTOMapper;
import com.ustu.erdbsystem.ermodels.api.mapper.RelationDTOMapper;
import com.ustu.erdbsystem.ermodels.exception.EnumValueException;
import com.ustu.erdbsystem.ermodels.exception.NotFoundException;
import com.ustu.erdbsystem.ermodels.exception.RelationDoesNotMatchEntityException;
import com.ustu.erdbsystem.ermodels.exception.RequestDataValidationException;
import com.ustu.erdbsystem.ermodels.service.ModelService;
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

    @GetMapping
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
                .orElseThrow(() -> new NotFoundException("Model with id=%d was not found!".formatted(id)));

        var personCredentialsDTO = PersonCredentialsDTOMapper.makeDTO(model.getPerson());
        var modelDTO = ModelDTOMapper.makeDTO(model);
        var modelEntityDTOList = model.getModelEntityList().stream()
                .map(ModelEntityDTOMapper::makeDTO)
                .toList();
        var modelEntityIdList = modelEntityDTOList.stream()
                .map(ModelEntityDTO::getId)
                .toList();
        var relationDTOList = modelService.getRelationsByEntityIds(modelEntityIdList).stream()
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
                .orElseThrow(() -> new NotFoundException("Person with id=%d was not found".formatted(createModelRequestDTO.getPersonId())));
        try {
            var modelDTO = ModelDTOMapper.makeDTO(createModelRequestDTO);
            var modelEntityDTOList = createModelRequestDTO.getTableList().stream()
                    .map(ModelEntityDTOMapper::makeDTO)
                    .toList();
            var relationDTOList = createModelRequestDTO.getRelationList().stream()
                    .map(RelationDTOMapper::makeDTO)
                    .toList();
            var modelId = modelService.create(person, modelDTO, modelEntityDTOList, relationDTOList);
            return ResponseEntity.ok(modelId);
        } catch (RelationDoesNotMatchEntityException | EnumValueException e) {
            throw new RequestDataValidationException("Validation error! " + e.getMessage(), e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteModel(@PathVariable Long id) {
        modelService.getById(id).ifPresentOrElse(
                modelService::deleteModel,
                () -> { throw new NotFoundException("Model with id=%d was not found".formatted(id)); }
        );
        return ResponseEntity.noContent().build();
    }
}

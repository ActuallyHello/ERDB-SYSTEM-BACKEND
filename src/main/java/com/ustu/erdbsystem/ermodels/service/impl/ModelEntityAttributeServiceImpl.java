package com.ustu.erdbsystem.ermodels.service.impl;

import com.ustu.erdbsystem.ermodels.api.dto.ModelEntityDTO;
import com.ustu.erdbsystem.ermodels.exception.service.ModelEntityCreationException;
import com.ustu.erdbsystem.ermodels.service.ModelEntityAttributeService;
import com.ustu.erdbsystem.ermodels.store.models.Attribute;
import com.ustu.erdbsystem.ermodels.store.models.Model;
import com.ustu.erdbsystem.ermodels.store.models.ModelEntity;
import com.ustu.erdbsystem.ermodels.store.models.enums.AttributeType;
import com.ustu.erdbsystem.ermodels.store.repos.ModelEntityRepo;
import jakarta.persistence.PersistenceException;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class ModelEntityAttributeServiceImpl implements ModelEntityAttributeService {

    private ModelEntityRepo modelEntityRepo;

    @Override
    @Transactional
    public List<ModelEntity> createModelEntities(List<ModelEntityDTO> modelEntityDTOList, Model model) {
        for (var modelEntityDTO : modelEntityDTOList) {
            ModelEntity modelEntity = ModelEntity.builder()
                    .title(modelEntityDTO.getTitle())
                    .build();
            for (var attributeDTO : modelEntityDTO.getAttributeDTOList()) {
                Attribute attribute = Attribute.builder()
                        .title(attributeDTO.getTitle())
                        .attributeType(AttributeType.fromString(attributeDTO.getAttributeType()))
                        .build();
                modelEntity.addAttribute(attribute);
            }
            model.addModelEntity(modelEntity);
        }
        try {
            modelEntityRepo.saveAllAndFlush(model.getModelEntityList());
            log.info("CREATED ENTITIES ({}) IN MODEL WITH ID={}", model.getModelEntityList().size(), model.getId());
            return model.getModelEntityList();
        } catch (DataIntegrityViolationException | PersistenceException exception) {
            log.error("ERROR WHEN CREATING ENTITIES WITH ATTRIBUTES: {}", exception.getMessage());
            throw new ModelEntityCreationException("Error when creating entities with attributes! [DatabaseException]", exception);
        }
    }

    @Override
    public List<ModelEntity> getAllByModel(Model model) {
        var modelEntityList = modelEntityRepo.getAllByModel(model);
        log.debug("GET ENTITIES ({})", modelEntityList.size());
        return modelEntityList;
    }
}

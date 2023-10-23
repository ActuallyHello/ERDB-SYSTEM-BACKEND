package com.ustu.erdbsystem.ermodels.service.impl;

import com.ustu.erdbsystem.ermodels.api.dto.ModelEntityDTO;
import com.ustu.erdbsystem.ermodels.service.ModelEntityAttributeService;
import com.ustu.erdbsystem.ermodels.store.models.Attribute;
import com.ustu.erdbsystem.ermodels.store.models.Model;
import com.ustu.erdbsystem.ermodels.store.models.ModelEntity;
import com.ustu.erdbsystem.ermodels.store.models.enums.AttributeType;
import com.ustu.erdbsystem.ermodels.store.repos.ModelEntityRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
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
        modelEntityRepo.saveAllAndFlush(model.getModelEntityList());
        log.info("CREATED %d ENTITIES IN MODEL WITH ID=%d".formatted(
                model.getModelEntityList().size(),
                model.getId()
        ));
        return model.getModelEntityList();
    }

    @Override
    @Transactional
    public List<ModelEntity> getAllByModel(Model model) {
        var modelEntityList = modelEntityRepo.getAllByModel(model);
        return modelEntityList;
    }
}

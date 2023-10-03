package com.ustu.erdbsystem.ermodels.api.mapper.impl;

import com.ustu.erdbsystem.ermodels.api.dto.ModelEntityDTO;
import com.ustu.erdbsystem.ermodels.api.mapper.DTOMapper;
import com.ustu.erdbsystem.ermodels.store.models.ModelEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ModelEntityDTOMapper implements DTOMapper<ModelEntityDTO, ModelEntity> {

    private AttributeDTOMapper attributeDTOFactory;

    @Override
    public ModelEntityDTO makeDTO(ModelEntity modelEntity) {
        if (modelEntity == null) throw new IllegalArgumentException("modelEntity is null!");
        var attributeDTOList = modelEntity.getAttributeList().stream()
                .map(attributeDTOFactory::makeDTO)
                .toList();
        return ModelEntityDTO.builder()
                .id(modelEntity.getId())
                .title(modelEntity.getTitle())
                .attributeDTOList(attributeDTOList)
                .build();
    }
}

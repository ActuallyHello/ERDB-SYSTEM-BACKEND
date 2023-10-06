package com.ustu.erdbsystem.ermodels.api.mapper;

import com.ustu.erdbsystem.ermodels.api.dto.AttributeDTO;
import com.ustu.erdbsystem.ermodels.api.dto.ModelEntityDTO;
import com.ustu.erdbsystem.ermodels.api.dto.request.CreateModelRequestDTO;
import com.ustu.erdbsystem.ermodels.api.dto.request.TableRequestDTO;
import com.ustu.erdbsystem.ermodels.store.models.ModelEntity;

public class ModelEntityDTOMapper {

    public static ModelEntityDTO makeDTO(ModelEntity modelEntity) {
        if (modelEntity == null) throw new IllegalArgumentException("modelEntity is null!");
        var attributeDTOList = modelEntity.getAttributeList().stream()
                .map(AttributeDTOMapper::makeDTO)
                .toList();
        return ModelEntityDTO.builder()
                .id(modelEntity.getId())
                .title(modelEntity.getTitle())
                .attributeDTOList(attributeDTOList)
                .build();
    }

    public static ModelEntityDTO makeDTO(TableRequestDTO tableRequestDTO) {
        if (tableRequestDTO == null) throw new IllegalArgumentException("tableRequestDTO is null!");
        var attributeDTOList = AttributeDTOMapper.makeDTO(tableRequestDTO);
        return ModelEntityDTO.builder()
                .title(tableRequestDTO.getTitle())
                .attributeDTOList(attributeDTOList)
                .build();
    }
}

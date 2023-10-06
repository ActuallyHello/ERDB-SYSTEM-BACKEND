package com.ustu.erdbsystem.ermodels.api.mapper;

import com.ustu.erdbsystem.ermodels.api.dto.AttributeDTO;
import com.ustu.erdbsystem.ermodels.api.dto.request.TableRequestDTO;
import com.ustu.erdbsystem.ermodels.store.models.Attribute;
import com.ustu.erdbsystem.ermodels.store.models.enums.AttributeType;

import java.util.ArrayList;
import java.util.List;

public class AttributeDTOMapper {
    public static AttributeDTO makeDTO(Attribute attribute) {
        if (attribute == null) throw new IllegalArgumentException("attribute is null!");
        return AttributeDTO.builder()
                .id(attribute.getId())
                .title(attribute.getTitle())
                .attributeType(attribute.getAttributeType().getValue())
                .build();
    }

    public static List<AttributeDTO> makeDTO(TableRequestDTO tableRequestDTO) {
        if (tableRequestDTO == null) throw new IllegalArgumentException("tableRequestDTO is null!");
        List<AttributeDTO> attributeDTOList = new ArrayList<>();
        tableRequestDTO.getPkFields().forEach(attribute -> {
            attributeDTOList.add(AttributeDTO.builder()
                    .title(attribute)
                    .attributeType(AttributeType.PRIMARY_KEY.getValue())
                    .build());
        });
        tableRequestDTO.getFkFields().forEach(attribute -> {
            attributeDTOList.add(AttributeDTO.builder()
                    .title(attribute)
                    .attributeType(AttributeType.FOREIGN_KEY.getValue())
                    .build());
        });
        tableRequestDTO.getAttrFields().forEach(attribute -> {
            attributeDTOList.add(AttributeDTO.builder()
                    .title(attribute)
                    .attributeType(AttributeType.ATTRIBUTE.getValue())
                    .build());
        });
        return attributeDTOList;
    }
}

package com.ustu.erdbsystem.ermodels.api.mapper.impl;

import com.ustu.erdbsystem.ermodels.api.dto.AttributeDTO;
import com.ustu.erdbsystem.ermodels.api.mapper.DTOMapper;
import com.ustu.erdbsystem.ermodels.store.models.Attribute;
import org.springframework.stereotype.Component;

@Component
public class AttributeDTOMapper implements DTOMapper<AttributeDTO, Attribute> {
    @Override
    public AttributeDTO makeDTO(Attribute attribute) {
        if (attribute == null) throw new IllegalArgumentException("attribute is null!");
        return AttributeDTO.builder()
                .id(attribute.getId())
                .title(attribute.getTitle())
                .attributeType(attribute.getAttributeType())
                .build();
    }
}

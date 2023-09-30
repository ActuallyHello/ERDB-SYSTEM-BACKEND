package com.ustu.erdbsystem.ermodels.factory.impl;

import com.ustu.erdbsystem.ermodels.dto.AttributeDTO;
import com.ustu.erdbsystem.ermodels.factory.DTOFactory;
import com.ustu.erdbsystem.ermodels.store.models.Attribute;
import org.springframework.stereotype.Component;

@Component
public class AttributeDTOFactory implements DTOFactory<AttributeDTO, Attribute> {
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

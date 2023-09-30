package com.ustu.erdbsystem.ermodels.dto;

import com.ustu.erdbsystem.ermodels.store.models.enums.AttributeType;
import lombok.Builder;

@Builder
public record AttributeDTO(
        Long id,
        String title,
        AttributeType attributeType
) {
}

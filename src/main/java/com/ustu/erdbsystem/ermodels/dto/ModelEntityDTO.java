package com.ustu.erdbsystem.ermodels.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record ModelEntityDTO(
        Long id,
        String title,
        List<AttributeDTO> attributeDTOList
) {
}

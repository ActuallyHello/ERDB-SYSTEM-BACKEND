package com.ustu.erdbsystem.ermodels.dto;

import com.ustu.erdbsystem.persons.dto.PersonCredentialsDTO;
import lombok.Builder;

import java.time.Instant;
import java.util.List;

@Builder
public record ModelDetailDTO(
        Long id,
        String title,
        String description,
        String topic,
        Instant createdAt,
        Instant updateAt,
        Boolean isTaskResult,
        PersonCredentialsDTO person,
        List<ModelEntityDTO> modelEntityDTOList
) {
}

package com.ustu.erdbsystem.ermodels.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ustu.erdbsystem.persons.api.dto.PersonCredentialsDTO;
import lombok.Builder;

import java.time.Instant;
import java.util.List;

@Builder
public record ModelDetailDTO(Long id,
                             String title,
                             String description,
                             String topic,
                             @JsonFormat(pattern="yyyy-MM-dd", timezone = "UTC") Instant createdAt,
                             @JsonFormat(pattern="yyyy-MM-dd", timezone = "UTC") Instant updateAt,
                             Boolean isTaskResult,
                             PersonCredentialsDTO person,
                             List<ModelEntityDTO> modelEntityDTOList
) {
}

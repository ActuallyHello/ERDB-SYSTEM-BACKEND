package com.ustu.erdbsystem.ermodels.dto;

import com.ustu.erdbsystem.persons.dto.PersonCredentialsDTO;
import lombok.Builder;

import java.time.Instant;

@Builder
public record ModelPreviewDTO(
        Long id,
        String title,
        String description,
        String topic,
        Instant createdAt,
        Instant updateAt,
        Boolean isTaskResult,
        PersonCredentialsDTO person
) {
}

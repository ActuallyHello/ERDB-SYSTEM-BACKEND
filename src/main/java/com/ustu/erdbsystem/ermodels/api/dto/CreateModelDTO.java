package com.ustu.erdbsystem.ermodels.api.dto;

import com.ustu.erdbsystem.persons.store.models.Person;
import lombok.Builder;

import java.util.List;

@Builder
public record CreateModelDTO(Person person,
                             String title,
                             String description,
                             String topic,
                             Boolean isTaskResult,
                             List<ModelEntityDTO> modelEntityDTOList) {
}

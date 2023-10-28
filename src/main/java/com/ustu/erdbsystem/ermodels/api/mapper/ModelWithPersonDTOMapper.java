package com.ustu.erdbsystem.ermodels.api.mapper;

import com.ustu.erdbsystem.ermodels.api.dto.response.ModelWithPersonDTO;
import com.ustu.erdbsystem.ermodels.store.models.Model;
import com.ustu.erdbsystem.persons.api.dto.PersonDTO;

public class ModelWithPersonDTOMapper {
    public static ModelWithPersonDTO makeDTO(Model model, PersonDTO personDTO) {
        return ModelWithPersonDTO.builder()
                .id(model.getId())
                .title(model.getTitle())
                .description(model.getDescription())
                .topic(model.getTopic())
                .createdAt(model.getCreatedAt())
                .updatedAt(model.getUpdatedAt())
                .isTaskResult(model.getIsTaskResult())
                .personDTO(personDTO)
                .build();
    }
}

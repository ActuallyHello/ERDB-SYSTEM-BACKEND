package com.ustu.erdbsystem.ermodels.api.mapper;

import com.ustu.erdbsystem.ermodels.api.dto.ModelEntityDTO;
import com.ustu.erdbsystem.ermodels.api.dto.RelationDTO;
import com.ustu.erdbsystem.ermodels.api.dto.response.ModelDetailDTO;
import com.ustu.erdbsystem.ermodels.store.models.Model;
import com.ustu.erdbsystem.persons.api.dto.PersonDTO;

import java.util.List;

public class ModelDetailDTOMapper {
    public static ModelDetailDTO makeDTO(Model model,
                                         PersonDTO personDTO,
                                         List<ModelEntityDTO> modelEntityDTOList,
                                         List<RelationDTO> relationDTOList) {
        return ModelDetailDTO.builder()
                .id(model.getId())
                .title(model.getTitle())
                .description(model.getDescription())
                .topic(model.getTopic())
                .createdAt(model.getCreatedAt())
                .updatedAt(model.getUpdatedAt())
                .isTaskResult(model.getIsTaskResult())
                .personDTO(personDTO)
                .modelEntityDTOList(modelEntityDTOList)
                .relationDTOList(relationDTOList)
                .build();
    }
}

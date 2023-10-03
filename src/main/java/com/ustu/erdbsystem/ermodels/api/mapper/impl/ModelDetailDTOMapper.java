package com.ustu.erdbsystem.ermodels.api.mapper.impl;

import com.ustu.erdbsystem.ermodels.api.dto.ModelDetailDTO;
import com.ustu.erdbsystem.ermodels.api.mapper.DTOMapper;
import com.ustu.erdbsystem.ermodels.store.models.Model;
import com.ustu.erdbsystem.persons.api.mapper.impl.PersonCredentialsDTOMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ModelDetailDTOMapper implements DTOMapper<ModelDetailDTO, Model> {

    private PersonCredentialsDTOMapper personCredentialsDTOFactory;
    private ModelEntityDTOMapper modelEntityDTOFactory;
    @Override
    public ModelDetailDTO makeDTO(Model model) {
        if (model == null) throw new IllegalArgumentException("model is null!");
        if (model.getPerson() == null) throw new IllegalArgumentException("model's person field is null!");
        var personCredentialsDTO = personCredentialsDTOFactory.makeDTO(model.getPerson());
        var modelEntityDTOList = model.getModelEntityList().stream()
                .map(modelEntityDTOFactory::makeDTO)
                .toList();
        return ModelDetailDTO.builder()
                .id(model.getId())
                .title(model.getTitle())
                .description(model.getDescription())
                .topic(model.getTopic())
                .createdAt(model.getCreatedAt())
                .updateAt(model.getUpdatedAt())
                .isTaskResult(model.getIsTaskResult())
                .person(personCredentialsDTO)
                .modelEntityDTOList(modelEntityDTOList)
                .build();
    }
}

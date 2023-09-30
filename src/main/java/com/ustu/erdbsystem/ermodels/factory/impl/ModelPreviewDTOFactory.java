package com.ustu.erdbsystem.ermodels.factory.impl;

import com.ustu.erdbsystem.ermodels.dto.ModelPreviewDTO;
import com.ustu.erdbsystem.ermodels.factory.DTOFactory;
import com.ustu.erdbsystem.ermodels.store.models.Model;
import com.ustu.erdbsystem.persons.dto.PersonCredentialsDTO;
import com.ustu.erdbsystem.persons.factory.impl.PersonCredentialsDTOFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ModelPreviewDTOFactory implements DTOFactory<ModelPreviewDTO, Model> {

    private PersonCredentialsDTOFactory personCredentialsDTOFactory;

    @Override
    public ModelPreviewDTO makeDTO(Model model) {
        if (model == null) throw new IllegalArgumentException("model is null!");
        if (model.getPerson() == null) throw new IllegalArgumentException("model's person field is null!");
        var personCredentialsDTO = personCredentialsDTOFactory.makeDTO(model.getPerson());
        return ModelPreviewDTO.builder()
                .id(model.getId())
                .title(model.getTitle())
                .description(model.getDescription())
                .topic(model.getTopic())
                .createdAt(model.getCreatedAt())
                .updateAt(model.getUpdatedAt())
                .isTaskResult(model.getIsTaskResult())
                .person(personCredentialsDTO)
                .build();
    }
}

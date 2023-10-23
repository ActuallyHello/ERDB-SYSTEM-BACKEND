package com.ustu.erdbsystem.ermodels.api.mapper;


import com.ustu.erdbsystem.ermodels.api.dto.ModelDTO;
import com.ustu.erdbsystem.ermodels.api.dto.request.CreateModelRequestDTO;
import com.ustu.erdbsystem.ermodels.store.models.Model;
import org.springframework.stereotype.Component;

public class ModelDTOMapper {
    public static ModelDTO makeDTO(Model model) {
        if (model == null) throw new IllegalArgumentException("model is null!");
        return ModelDTO.builder()
                .id(model.getId())
                .title(model.getTitle())
                .description(model.getDescription())
                .topic(model.getTopic())
                .createdAt(model.getCreatedAt())
                .updatedAt(model.getUpdatedAt())
                .isTaskResult(model.getIsTaskResult())
                .build();
    }

    public static ModelDTO makeDTO(CreateModelRequestDTO createModelRequestDTO) {
        if (createModelRequestDTO == null) throw new IllegalArgumentException("createModelRequestDTO is null");
        return ModelDTO.builder()
                .title(createModelRequestDTO.getTitle())
                .description(createModelRequestDTO.getDescription())
                .topic(createModelRequestDTO.getTopic())
                .isTaskResult(createModelRequestDTO.getIsTaskResult())
                .build();
    }

    public static Model fromDTO(ModelDTO modelDTO) {
        if (modelDTO == null) throw new IllegalArgumentException("modelDTO is null!");
        return Model.builder()
                .title(modelDTO.getTitle())
                .description(modelDTO.getDescription())
                .topic(modelDTO.getTopic())
                .isTaskResult(modelDTO.getIsTaskResult())
                .build();
    }
}

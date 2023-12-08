package com.ustu.erdbsystem.ermodels.api.mapper;


import com.ustu.erdbsystem.ermodels.api.dto.ModelDTO;
import com.ustu.erdbsystem.ermodels.api.dto.request.CreateModelRequestDTO;
import com.ustu.erdbsystem.ermodels.store.models.Model;
import lombok.NonNull;
import org.springframework.stereotype.Component;

public class ModelDTOMapper {
    public static ModelDTO makeDTO(@NonNull Model model) {
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

    public static ModelDTO makeDTO(@NonNull CreateModelRequestDTO createModelRequestDTO) {
        return ModelDTO.builder()
                .title(createModelRequestDTO.getTitle())
                .description(createModelRequestDTO.getDescription())
                .topic(createModelRequestDTO.getTopic())
                .isTaskResult(createModelRequestDTO.getIsTaskResult())
                .build();
    }

    public static Model fromDTO(@NonNull ModelDTO modelDTO) {
        return Model.builder()
                .id(modelDTO.getId())
                .title(modelDTO.getTitle())
                .description(modelDTO.getDescription())
                .topic(modelDTO.getTopic())
                .isTaskResult(modelDTO.getIsTaskResult())
                .build();
    }
}

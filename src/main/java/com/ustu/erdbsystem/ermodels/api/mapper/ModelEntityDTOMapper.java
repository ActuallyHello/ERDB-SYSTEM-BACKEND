package com.ustu.erdbsystem.ermodels.api.mapper;

import com.ustu.erdbsystem.ermodels.api.dto.AttributeDTO;
import com.ustu.erdbsystem.ermodels.api.dto.ModelEntityDTO;
import com.ustu.erdbsystem.ermodels.api.dto.request.CreateModelRequestDTO;
import com.ustu.erdbsystem.ermodels.api.dto.request.TableRequestDTO;
import com.ustu.erdbsystem.ermodels.store.models.ModelEntity;
import lombok.NonNull;

import java.util.List;

public class ModelEntityDTOMapper {

    public static ModelEntityDTO makeDTO(@NonNull ModelEntity modelEntity,
                                         @NonNull List<AttributeDTO> attributeDTOList) {
        return ModelEntityDTO.builder()
                .id(modelEntity.getId())
                .title(modelEntity.getTitle())
                .attributeDTOList(attributeDTOList)
                .build();
    }

    public static ModelEntityDTO makeDTO(@NonNull TableRequestDTO tableRequestDTO,
                                         @NonNull List<AttributeDTO> attributeDTOList) {
        return ModelEntityDTO.builder()
                .title(tableRequestDTO.getTitle())
                .attributeDTOList(attributeDTOList)
                .build();
    }
}

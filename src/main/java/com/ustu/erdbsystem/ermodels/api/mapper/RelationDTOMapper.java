package com.ustu.erdbsystem.ermodels.api.mapper;

import com.ustu.erdbsystem.ermodels.api.dto.RelationDTO;
import com.ustu.erdbsystem.ermodels.api.dto.request.RelationRequestDTO;
import com.ustu.erdbsystem.ermodels.store.models.Relation;
import com.ustu.erdbsystem.ermodels.store.models.enums.Power;
import lombok.NonNull;

public class RelationDTOMapper {

    public static RelationDTO makeDTO(@NonNull Relation relation) {
        return RelationDTO.builder()
                .id(relation.getId())
                .fromEntity(relation.getModelEntity1().getTitle())
                .power(relation.getPower().getValue())
                .toEntity(relation.getModelEntity2().getTitle())
                .build();
    }

    public static RelationDTO makeDTO(@NonNull RelationRequestDTO relationRequestDTO) {
        return RelationDTO.builder()
                .fromEntity(relationRequestDTO.getFromEntity())
                .power(Power.fromString(relationRequestDTO.getPower()).getValue())
                .toEntity(relationRequestDTO.getToEntity())
                .build();
    }
}

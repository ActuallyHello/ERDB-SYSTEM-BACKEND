package com.ustu.erdbsystem.ermodels.api.mapper;

import com.ustu.erdbsystem.ermodels.api.dto.RelationDTO;
import com.ustu.erdbsystem.ermodels.api.dto.request.RelationRequestDTO;
import com.ustu.erdbsystem.ermodels.store.models.Relation;
import com.ustu.erdbsystem.ermodels.store.models.enums.Power;

public class RelationDTOMapper {

    public static RelationDTO makeDTO(Relation relation) {
        if (relation == null) throw new IllegalArgumentException("relation is null!");
        return RelationDTO.builder()
                .id(relation.getId())
                .fromEntity(relation.getModelEntity1().getTitle())
                .power(relation.getPower().getValue())
                .toEntity(relation.getModelEntity2().getTitle())
                .build();
    }

    public static RelationDTO makeDTO(RelationRequestDTO relationRequestDTO) {
        if (relationRequestDTO == null) throw new IllegalArgumentException("relationRequestDTO is null");
        return RelationDTO.builder()
                .fromEntity(relationRequestDTO.getFromEntity())
                .power(Power.fromString(relationRequestDTO.getPower()).getValue())
                .toEntity(relationRequestDTO.getToEntity())
                .build();
    }
}

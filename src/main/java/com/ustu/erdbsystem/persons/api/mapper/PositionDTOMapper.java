package com.ustu.erdbsystem.persons.api.mapper;

import com.ustu.erdbsystem.persons.api.dto.PositionDTO;
import com.ustu.erdbsystem.persons.store.models.Position;

public class PositionDTOMapper {

    public static PositionDTO makeDTO(Position position) {
        if (position == null) throw new IllegalArgumentException("position is null");
        return PositionDTO.builder()
                .id(position.getId())
                .title(position.getTitle())
                .build();
    }

    public static Position fromDTO(PositionDTO positionDTO) {
        if (positionDTO == null) throw new IllegalArgumentException("positionDTO is null");
        return Position.builder()
                .id(positionDTO.getId())
                .title(positionDTO.getTitle())
                .build();
    }

}

package com.ustu.erdbsystem.persons.api.mapper;

import com.ustu.erdbsystem.persons.api.dto.PositionDTO;
import com.ustu.erdbsystem.persons.api.dto.request.CreatePositionRequestDTO;
import com.ustu.erdbsystem.persons.store.models.Position;

public class PositionDTOMapper {

    public static PositionDTO makeDTO(Position position) {
        return PositionDTO.builder()
                .id(position.getId())
                .title(position.getTitle())
                .build();
    }

    public static PositionDTO makeDTO(CreatePositionRequestDTO position) {
        return PositionDTO.builder()
                .title(position.getTitle())
                .build();
    }

    public static Position fromDTO(PositionDTO positionDTO) {
        return Position.builder()
                .id(positionDTO.getId())
                .title(positionDTO.getTitle())
                .build();
    }

}

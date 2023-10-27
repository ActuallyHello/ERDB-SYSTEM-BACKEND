package com.ustu.erdbsystem.persons.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class TeacherWithPositionDTO {
    private Long id;
    private PositionDTO positionDTO;
}

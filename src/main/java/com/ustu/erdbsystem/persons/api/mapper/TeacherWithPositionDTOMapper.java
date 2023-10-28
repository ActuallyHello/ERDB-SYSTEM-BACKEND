package com.ustu.erdbsystem.persons.api.mapper;

import com.ustu.erdbsystem.persons.api.dto.PositionDTO;
import com.ustu.erdbsystem.persons.api.dto.response.TeacherWithPositionDTO;
import com.ustu.erdbsystem.persons.store.models.Teacher;

public class TeacherWithPositionDTOMapper {
    public static TeacherWithPositionDTO makeDTO(Teacher teacher, PositionDTO positionDTO) {
        return TeacherWithPositionDTO.builder()
                .id(teacher.getId())
                .positionDTO(positionDTO)
                .build();
    }
}

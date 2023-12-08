package com.ustu.erdbsystem.persons.api.mapper;

import com.ustu.erdbsystem.persons.api.dto.PositionDTO;
import com.ustu.erdbsystem.persons.api.dto.response.TeacherWithPositionDTO;
import com.ustu.erdbsystem.persons.store.models.Teacher;
import lombok.NonNull;

public class TeacherWithPositionDTOMapper {
    public static TeacherWithPositionDTO makeDTO(@NonNull Teacher teacher,
                                                 @NonNull PositionDTO positionDTO) {
        return TeacherWithPositionDTO.builder()
                .id(teacher.getId())
                .positionDTO(positionDTO)
                .build();
    }
}

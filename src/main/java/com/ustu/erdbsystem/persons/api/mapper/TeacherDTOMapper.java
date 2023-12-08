package com.ustu.erdbsystem.persons.api.mapper;

import com.ustu.erdbsystem.persons.api.dto.PersonDTO;
import com.ustu.erdbsystem.persons.api.dto.PositionDTO;
import com.ustu.erdbsystem.persons.api.dto.TeacherDTO;
import com.ustu.erdbsystem.persons.store.models.Teacher;
import lombok.NonNull;

public class TeacherDTOMapper {

    public static TeacherDTO makeDTO(@NonNull Teacher teacher,
                                     @NonNull PersonDTO personDTO,
                                     @NonNull PositionDTO positionDTO) {
        return TeacherDTO.builder()
                .id(teacher.getId())
                .personDTO(personDTO)
                .positionDTO(positionDTO)
                .build();
    }
}

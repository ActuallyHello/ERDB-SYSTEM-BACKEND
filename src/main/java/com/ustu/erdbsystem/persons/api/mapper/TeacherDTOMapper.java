package com.ustu.erdbsystem.persons.api.mapper;

import com.ustu.erdbsystem.persons.api.dto.PersonDTO;
import com.ustu.erdbsystem.persons.api.dto.PositionDTO;
import com.ustu.erdbsystem.persons.api.dto.TeacherDTO;
import com.ustu.erdbsystem.persons.store.models.Teacher;

public class TeacherDTOMapper {

    public static TeacherDTO makeDTO(Teacher teacher, PersonDTO personDTO, PositionDTO positionDTO) {
        return TeacherDTO.builder()
                .id(teacher.getId())
                .personDTO(personDTO)
                .positionDTO(positionDTO)
                .build();
    }
}

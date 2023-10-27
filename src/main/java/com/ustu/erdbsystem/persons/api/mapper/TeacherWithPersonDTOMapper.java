package com.ustu.erdbsystem.persons.api.mapper;

import com.ustu.erdbsystem.persons.api.dto.PersonDTO;
import com.ustu.erdbsystem.persons.api.dto.TeacherWithPersonDTO;
import com.ustu.erdbsystem.persons.store.models.Teacher;

public class TeacherWithPersonDTOMapper {

    public static TeacherWithPersonDTO makeDTO(Teacher teacher, PersonDTO personDTO) {
        return TeacherWithPersonDTO.builder()
                .id(teacher.getId())
                .personDTO(personDTO)
                .build();
    }
}

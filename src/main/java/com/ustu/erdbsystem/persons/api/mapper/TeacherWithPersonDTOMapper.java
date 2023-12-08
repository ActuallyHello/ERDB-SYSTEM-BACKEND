package com.ustu.erdbsystem.persons.api.mapper;

import com.ustu.erdbsystem.persons.api.dto.PersonDTO;
import com.ustu.erdbsystem.persons.api.dto.response.TeacherWithPersonDTO;
import com.ustu.erdbsystem.persons.store.models.Teacher;
import lombok.NonNull;

public class TeacherWithPersonDTOMapper {

    public static TeacherWithPersonDTO makeDTO(@NonNull Teacher teacher,
                                               @NonNull PersonDTO personDTO) {
        return TeacherWithPersonDTO.builder()
                .id(teacher.getId())
                .personDTO(personDTO)
                .build();
    }
}

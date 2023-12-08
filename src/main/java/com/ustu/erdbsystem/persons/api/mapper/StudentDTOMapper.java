package com.ustu.erdbsystem.persons.api.mapper;

import com.ustu.erdbsystem.persons.api.dto.GroupDTO;
import com.ustu.erdbsystem.persons.api.dto.PersonDTO;
import com.ustu.erdbsystem.persons.api.dto.StudentDTO;
import com.ustu.erdbsystem.persons.store.models.Student;
import lombok.NonNull;

public class StudentDTOMapper {

    public static StudentDTO makeDTO(@NonNull GroupDTO groupDTO,
                                     @NonNull PersonDTO personDTO) {
        return StudentDTO.builder()
                .groupDTO(groupDTO)
                .personDTO(personDTO)
                .build();
    }
}

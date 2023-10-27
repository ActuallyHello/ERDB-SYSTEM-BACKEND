package com.ustu.erdbsystem.persons.api.mapper;

import com.ustu.erdbsystem.persons.api.dto.GroupDTO;
import com.ustu.erdbsystem.persons.api.dto.PersonDTO;
import com.ustu.erdbsystem.persons.api.dto.StudentDTO;
import com.ustu.erdbsystem.persons.store.models.Student;

public class StudentDTOMapper {

    public static StudentDTO makeDTO(GroupDTO groupDTO, PersonDTO personDTO) {
        return StudentDTO.builder()
                .groupDTO(groupDTO)
                .personDTO(personDTO)
                .build();
    }
}

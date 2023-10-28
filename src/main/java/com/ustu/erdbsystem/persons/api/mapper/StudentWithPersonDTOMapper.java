package com.ustu.erdbsystem.persons.api.mapper;

import com.ustu.erdbsystem.persons.api.dto.PersonDTO;
import com.ustu.erdbsystem.persons.api.dto.response.StudentWithPersonDTO;
import com.ustu.erdbsystem.persons.store.models.Student;

public class StudentWithPersonDTOMapper {
    public static StudentWithPersonDTO makeDTO(Student student, PersonDTO personDTO) {
        return StudentWithPersonDTO.builder()
                .id(student.getId())
                .personDTO(personDTO)
                .build();
    }
}

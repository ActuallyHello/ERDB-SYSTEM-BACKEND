package com.ustu.erdbsystem.persons.api.mapper;

import com.ustu.erdbsystem.persons.api.dto.GroupDTO;
import com.ustu.erdbsystem.persons.api.dto.StudentWithGroupDTO;
import com.ustu.erdbsystem.persons.store.models.Student;

public class StudentWithGroupDTOMapper {
    public static StudentWithGroupDTO makeDTO(Student student, GroupDTO groupDTO) {
        return StudentWithGroupDTO.builder()
                .id(student.getId())
                .groupDTO(groupDTO)
                .build();
    }
}

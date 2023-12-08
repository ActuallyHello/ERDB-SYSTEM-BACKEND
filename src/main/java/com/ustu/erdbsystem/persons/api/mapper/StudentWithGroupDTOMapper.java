package com.ustu.erdbsystem.persons.api.mapper;

import com.ustu.erdbsystem.persons.api.dto.GroupDTO;
import com.ustu.erdbsystem.persons.api.dto.response.StudentWithGroupDTO;
import com.ustu.erdbsystem.persons.store.models.Student;
import lombok.NonNull;

public class StudentWithGroupDTOMapper {
    public static StudentWithGroupDTO makeDTO(@NonNull Student student,
                                              @NonNull GroupDTO groupDTO) {
        return StudentWithGroupDTO.builder()
                .id(student.getId())
                .groupDTO(groupDTO)
                .build();
    }
}

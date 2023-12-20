package com.ustu.erdbsystem.tasks.api.mapper;

import com.ustu.erdbsystem.persons.api.dto.StudentDTO;
import com.ustu.erdbsystem.persons.api.dto.TeacherDTO;
import com.ustu.erdbsystem.tasks.api.dtos.TaskDTO;
import com.ustu.erdbsystem.tasks.api.dtos.response.ResultWithTaskDTO;
import com.ustu.erdbsystem.tasks.store.models.Result;
import lombok.NonNull;

public class ResultWithTaskDTOMapper {

    public static ResultWithTaskDTO makeDTO(@NonNull Result result,
                                            @NonNull TaskDTO taskDTO,
                                            @NonNull StudentDTO studentDTO,
                                            TeacherDTO teacherDTO) {
        return ResultWithTaskDTO.builder()
                .id(result.getId())
                .mark(result.getMark())
                .createdAt(result.getCreatedAt())
                .updatedAt(result.getUpdatedAt())
                .taskDTO(taskDTO)
                .studentDTO(studentDTO)
                .teacherDTO(teacherDTO)
                .build();
    }
}

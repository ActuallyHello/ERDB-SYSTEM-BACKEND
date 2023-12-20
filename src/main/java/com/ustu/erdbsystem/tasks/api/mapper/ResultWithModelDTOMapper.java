package com.ustu.erdbsystem.tasks.api.mapper;

import com.ustu.erdbsystem.ermodels.api.dto.response.ModelDetailDTO;
import com.ustu.erdbsystem.persons.api.dto.StudentDTO;
import com.ustu.erdbsystem.persons.api.dto.TeacherDTO;
import com.ustu.erdbsystem.persons.api.dto.response.StudentWithGroupDTO;
import com.ustu.erdbsystem.persons.api.dto.response.TeacherWithPositionDTO;
import com.ustu.erdbsystem.tasks.api.dtos.TaskDTO;
import com.ustu.erdbsystem.tasks.api.dtos.response.ResultWithModelDTO;
import com.ustu.erdbsystem.tasks.store.models.Result;
import lombok.NonNull;

public class ResultWithModelDTOMapper {

    public static ResultWithModelDTO makeDTO(@NonNull Result result,
                                             @NonNull ModelDetailDTO modelDetailSourceDTO,
                                             @NonNull ModelDetailDTO modelDetailResultDTO,
                                             @NonNull TaskDTO taskDTO,
                                             TeacherDTO teacherDTO) {
        return ResultWithModelDTO.builder()
                .id(result.getId())
                .mark(result.getMark())
                .createdAt(result.getCreatedAt())
                .updatedAt(result.getUpdatedAt())
                .modelDetailSourceDTO(modelDetailSourceDTO)
                .modelDetailResultDTO(modelDetailResultDTO)
                .taskDTO(taskDTO)
                .teacherDTO(teacherDTO)
                .build();
    }
}

package com.ustu.erdbsystem.tasks.api.mapper;

import com.ustu.erdbsystem.ermodels.api.dto.response.ModelDetailDTO;
import com.ustu.erdbsystem.persons.api.dto.response.TeacherWithPositionDTO;
import com.ustu.erdbsystem.tasks.api.dtos.TaskTitleDTO;
import com.ustu.erdbsystem.tasks.api.dtos.response.ResultWithModelDTO;
import com.ustu.erdbsystem.tasks.store.models.Result;

public class ResultWithModelDTOMapper {

    public static ResultWithModelDTO makeDTO(Result result,
                                             ModelDetailDTO modelDetailSourceDTO,
                                             ModelDetailDTO modelDetailResultDTO,
                                             TeacherWithPositionDTO teacherWithPositionDTO,
                                             TaskTitleDTO taskTitleDTO) {
        return ResultWithModelDTO.builder()
                .id(result.getId())
                .mark(result.getMark().getValue())
                .createdAt(result.getCreatedAt())
                .updatedAt(result.getUpdatedAt())
                .modelDetailSourceDTO(modelDetailSourceDTO)
                .modelDetailResultDTO(modelDetailResultDTO)
                .teacherWithPositionDTO(teacherWithPositionDTO)
                .taskTitleDTO(taskTitleDTO)
                .build();
    }
}

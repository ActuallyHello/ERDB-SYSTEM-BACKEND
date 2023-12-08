package com.ustu.erdbsystem.tasks.api.mapper;

import com.ustu.erdbsystem.tasks.api.dtos.ResultDTO;
import com.ustu.erdbsystem.tasks.api.dtos.request.CreateResultRequestDTO;
import com.ustu.erdbsystem.tasks.store.models.Result;
import com.ustu.erdbsystem.tasks.store.models.enums.Mark;

public class ResultDTOMapper {

    public static ResultDTO makeDTO(Result result) {
        return ResultDTO.builder()
                .id(result.getId())
                .mark(result.getMark().getValue())
                .createdAt(result.getCreatedAt())
                .updatedAt(result.getUpdatedAt())
                .build();
    }

    public static ResultDTO makeDTO(CreateResultRequestDTO createResultRequestDTO) {
        return ResultDTO.builder()
                .mark(createResultRequestDTO.getMark())
                .build();
    }

    public static Result fromDTO(ResultDTO resultDTO) {
        return Result.builder()
                .mark(Mark.fromInteger(resultDTO.getMark()))
                .build();
    }
}

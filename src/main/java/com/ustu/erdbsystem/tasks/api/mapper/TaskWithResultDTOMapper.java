package com.ustu.erdbsystem.tasks.api.mapper;

import com.ustu.erdbsystem.persons.api.dto.TeacherDTO;
import com.ustu.erdbsystem.tasks.api.dtos.ResultDTO;
import com.ustu.erdbsystem.tasks.api.dtos.response.TaskWithResultDTO;
import com.ustu.erdbsystem.tasks.store.models.Task;

public class TaskWithResultDTOMapper {

    public static TaskWithResultDTO makeDTO(Task task, TeacherDTO teacherDTO, ResultDTO resultDTO) {
        return TaskWithResultDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .complexity(task.getComplexity())
                .testDataAmount(task.getTestDataAmount())
                .teacherDTO(teacherDTO)
                .resultDTO(resultDTO)
                .build();
    }
}

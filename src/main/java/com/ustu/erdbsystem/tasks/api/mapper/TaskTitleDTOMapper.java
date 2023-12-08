package com.ustu.erdbsystem.tasks.api.mapper;

import com.ustu.erdbsystem.tasks.api.dtos.TaskTitleDTO;
import com.ustu.erdbsystem.tasks.store.models.Task;

public class TaskTitleDTOMapper {

    public static TaskTitleDTO makeDTO(Task task) {
        return TaskTitleDTO.builder()
                .title(task.getTitle())
                .description(task.getDescription())
                .updatedAt(task.getUpdatedAt())
                .build();
    }
}

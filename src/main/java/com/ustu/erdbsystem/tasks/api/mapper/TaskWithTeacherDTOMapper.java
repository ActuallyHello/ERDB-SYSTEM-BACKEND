package com.ustu.erdbsystem.tasks.api.mapper;


import com.ustu.erdbsystem.persons.api.dto.TeacherDTO;
import com.ustu.erdbsystem.tasks.api.dtos.response.TaskWithTeacherDTO;
import com.ustu.erdbsystem.tasks.store.models.Task;

public class TaskWithTeacherDTOMapper {
    public static TaskWithTeacherDTO makeDTO(Task task, TeacherDTO teacherDTO) {
        return TaskWithTeacherDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .testDataAmount(task.getTestDataAmount())
                .complexity(task.getComplexity())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .teacherDTO(teacherDTO)
                .build();
    }
}

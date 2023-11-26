package com.ustu.erdbsystem.tasks.api.dtos.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTasksStudentsRequestDTO {
    @NotNull(message = "taskIds must be not null!")
    @NotEmpty(message = "taskIds must be not empty!")
    private List<Long> taskIds;

    @NotNull(message = "studentIds must be not null!")
    @NotEmpty(message = "studentIds must be not empty!")
    private List<Long> studentIds;
}

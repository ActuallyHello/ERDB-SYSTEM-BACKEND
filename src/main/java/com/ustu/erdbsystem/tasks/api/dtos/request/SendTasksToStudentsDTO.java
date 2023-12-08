package com.ustu.erdbsystem.tasks.api.dtos.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class SendTasksToStudentsDTO {
    @NotNull(message = "taskIds must be not null!")
    @NotEmpty(message = "taskIds must be not empty!")
    private List<Long> taskIds;

    @NotNull(message = "studentIds must be not null!")
    @NotEmpty(message = "studentIds must be not empty!")
    private List<Long> studentIds;
}

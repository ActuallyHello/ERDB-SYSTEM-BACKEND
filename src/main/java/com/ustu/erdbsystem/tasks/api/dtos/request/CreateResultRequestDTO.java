package com.ustu.erdbsystem.tasks.api.dtos.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateResultRequestDTO {
    @NotNull(message = "teacherId must be not null!")
    @Min(value = 1, message = "teacherId cannot be less than 1!")
    private Long teacherId;

    @NotNull(message = "modelId must be not null!")
    @Min(value = 1, message = "modelId cannot be less than 1!")
    private Long modelId;

    @NotNull(message = "taskId must be not null!")
    @Min(value = 1, message = "taskId cannot be less than 1!")
    private Long taskId;

    @NotNull(message = "mark must be not null!")
    @Min(value = 2, message = "mark cannot be less than 2!")
    @Max(value = 5, message = "mark cannot be greater than 5!")
    private Integer mark;

    @NotNull(message = "complexity must be not null!")
    @NotBlank(message = "complexity must be not empty!")
    private String complexity;
}

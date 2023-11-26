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

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaskRequestDTO {
    @NotNull(message = "teacherId must be not null!")
    @Min(value = 1, message = "teacherId cannot be less than 1!")
    private Long teacherId;

    @NotNull(message = "title must be not null!")
    @NotBlank(message = "title must be not empty!")
    private String title;

    @NotNull(message = "description must be not null!")
    @NotBlank(message = "description must be not empty!")
    private String description;

    @NotNull(message = "testDataAmount must be not null!")
    @Min(value = 5, message = "testDataAmount cannot be less than 5!")
    @Max(value = 1000, message = "testDataAmount cannot be greater than 1000!")
    private Integer testDataAmount;

    @NotNull(message = "complexity must be not null!")
    @NotBlank(message = "complexity must be not empty!")
    private String complexity;

    @NotNull(message = "modelIds must be not null!")
    @NotEmpty(message = "modelIds must be not empty!")
    private List<Long> modelIds;
}

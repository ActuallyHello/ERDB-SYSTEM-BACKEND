package com.ustu.erdbsystem.ermodels.api.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateModelRequestDTO {
    @NotNull(message = "personId must be not null!")
    @Min(value = 1, message = "personId cannot be less than 1!")
    private Long personId;

    @NotNull(message = "title must be not null!")
    @NotBlank(message = "title must be not empty!")
    private String title;

    @NotNull(message = "description must be not null!")
    @NotBlank(message = "description must be not empty!")
    private String description;

    @NotNull(message = "topic must be not null!")
    @NotBlank(message = "topic must be not empty!")
    private String topic;

    @Builder.Default
    private Boolean isTaskResult = false;

    @NotEmpty(message = "tableList must be not empty!")
    @Valid
    @Builder.Default
    private List<TableRequestDTO> tableList = new ArrayList<>();

    @NotEmpty(message = "relationList must be not empty!")
    @Valid
    @Builder.Default
    private List<RelationRequestDTO> relationList = new ArrayList<>();
}

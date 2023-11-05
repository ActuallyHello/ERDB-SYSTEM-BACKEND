package com.ustu.erdbsystem.persons.api.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTeacherRequestDTO {
    @NotNull(message = "personId must be not null!")
    @Min(value = 1, message = "personId cannot be less than 1!")
    private Long personId;

    @NotNull(message = "positionId must be not null!")
    @Min(value = 1, message = "positionId cannot be less than 1!")
    private Long positionId;
}

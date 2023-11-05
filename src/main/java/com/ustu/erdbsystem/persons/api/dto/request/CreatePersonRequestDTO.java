package com.ustu.erdbsystem.persons.api.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePersonRequestDTO {
    @NotNull(message = "title must be not null!")
    @NotBlank(message = "title must be not empty!")
    private String firstName;

    @NotNull(message = "lastName must be not null!")
    @NotBlank(message = "lastName must be not empty!")
    private String lastName;

    private String middleName;

    @NotNull(message = "personType must be not null!")
    @NotBlank(message = "personType must be not empty!")
    private String personType;

    @NotNull(message = "userId must be not null!")
    @Min(value = 1, message = "userId cannot be less than 1!")
    private Long userId;
}

package com.ustu.erdbsystem.ermodels.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RelationRequestDTO {
    @NotNull(message = "fromEntity must be not null!")
    @NotBlank(message = "fromEntity must be not empty!")
    private String fromEntity;

    @NotNull(message = "power must be not null!")
    @NotBlank(message = "power must be not empty!")
    @Pattern(regexp = "^[1|N]-[1|N]$", message = "power must match pattern='^[1|N]-[1|N]$'")
    private String power;

    @NotNull(message = "toEntity must be not null!")
    @NotBlank(message = "toEntity must be not empty!")
    private String toEntity;
}

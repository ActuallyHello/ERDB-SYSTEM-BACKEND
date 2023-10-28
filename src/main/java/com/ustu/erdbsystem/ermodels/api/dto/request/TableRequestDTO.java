package com.ustu.erdbsystem.ermodels.api.dto.request;

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
public class TableRequestDTO {
    @NotNull(message = "power must be not null!")
    @NotBlank(message = "power must be not empty!")
    private String title;

    @NotNull(message = "pkFields must be not null!")
    private List<String> pkFields;

    @NotNull(message = "fkFields must be not null!")
    private List<String> fkFields;

    @NotNull(message = "attrFields must be not null!")
    private List<String> attrFields;
}

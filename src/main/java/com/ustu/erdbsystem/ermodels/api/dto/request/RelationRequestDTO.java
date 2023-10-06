package com.ustu.erdbsystem.ermodels.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RelationRequestDTO {
    private String fromEntity;
    private String power;
    private String toEntity;
}

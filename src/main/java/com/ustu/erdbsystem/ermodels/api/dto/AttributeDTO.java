package com.ustu.erdbsystem.ermodels.api.dto;

import com.ustu.erdbsystem.ermodels.store.models.enums.AttributeType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AttributeDTO {
    private Long id;
    private String title;
    private String attributeType;
}

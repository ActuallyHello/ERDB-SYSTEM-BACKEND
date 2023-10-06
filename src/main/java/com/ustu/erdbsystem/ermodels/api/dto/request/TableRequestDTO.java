package com.ustu.erdbsystem.ermodels.api.dto.request;

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
    private String title;
    private List<String> pkFields;
    private List<String> fkFields;
    private List<String> attrFields;
}

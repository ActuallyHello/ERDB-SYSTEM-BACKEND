package com.ustu.erdbsystem.ermodels.api.dto.request;

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
    private Long personId;
    private String title;
    private String description;
    private String topic;
    @Builder.Default
    private Boolean isTaskResult = false;
    @Builder.Default
    private List<TableRequestDTO> tableList = new ArrayList<>();
    @Builder.Default
    private List<RelationRequestDTO> relationList = new ArrayList<>();
}

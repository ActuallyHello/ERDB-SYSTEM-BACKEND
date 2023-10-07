package com.ustu.erdbsystem.ermodels.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ModelDTO {
    private Long id;
    private String title;
    private String description;
    private String topic;
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "UTC")
    private Instant createdAt;
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "UTC")
    private Instant updatedAt;
    private Boolean isTaskResult = false;
}
package com.ustu.erdbsystem.tasks.api.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultDTO {
    private Long id;
    private Integer mark;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    private Instant createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    private Instant updatedAt;
}

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
public class DenormalizeModel {
    private Long id;
    private String view;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    private Instant updatedAt;
}

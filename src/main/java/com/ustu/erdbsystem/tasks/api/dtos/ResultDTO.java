package com.ustu.erdbsystem.tasks.api.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ustu.erdbsystem.tasks.store.models.enums.Mark;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ResultDTO {
    private Long id;
    private Integer mark;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    private Instant createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    private Instant updatedAt;
}

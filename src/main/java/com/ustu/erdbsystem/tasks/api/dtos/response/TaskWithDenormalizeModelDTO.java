package com.ustu.erdbsystem.tasks.api.dtos.response;

import com.ustu.erdbsystem.tasks.api.dtos.DenormalizeModel;
import com.ustu.erdbsystem.tasks.api.dtos.TaskDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskWithDenormalizeModelDTO {
    private TaskDTO taskDTO;
    private DenormalizeModel denormalizeModel;
}

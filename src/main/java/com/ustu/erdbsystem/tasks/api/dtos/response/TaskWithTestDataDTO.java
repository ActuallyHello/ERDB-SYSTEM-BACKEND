package com.ustu.erdbsystem.tasks.api.dtos.response;

import com.ustu.erdbsystem.tasks.api.dtos.TaskDTO;
import com.ustu.erdbsystem.tasks.api.dtos.TestDataDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class TaskWithTestDataDTO {
    public final TaskDTO taskDTO;
    public final TestDataDTO testDataDTO;
}

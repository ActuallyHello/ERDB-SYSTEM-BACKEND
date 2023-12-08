package com.ustu.erdbsystem.tasks.api.dtos.response;

import com.ustu.erdbsystem.tasks.api.dtos.TaskDTO;
import com.ustu.erdbsystem.tasks.api.dtos.TestDataDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@SuperBuilder
@Getter
public class TaskWithTestDataDTO extends TaskDTO {
    public final TestDataDTO testDataDTO;
}

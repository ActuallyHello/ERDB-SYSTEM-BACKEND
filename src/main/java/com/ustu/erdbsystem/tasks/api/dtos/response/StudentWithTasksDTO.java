package com.ustu.erdbsystem.tasks.api.dtos.response;

import com.ustu.erdbsystem.persons.api.dto.StudentDTO;
import com.ustu.erdbsystem.tasks.api.dtos.TaskDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentWithTasksDTO {
    private StudentDTO studentDTO;
    private List<TaskDTO> taskDTOList;
}

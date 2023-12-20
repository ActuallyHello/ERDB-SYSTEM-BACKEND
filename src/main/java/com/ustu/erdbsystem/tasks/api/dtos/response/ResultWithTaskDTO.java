package com.ustu.erdbsystem.tasks.api.dtos.response;

import com.ustu.erdbsystem.persons.api.dto.StudentDTO;
import com.ustu.erdbsystem.persons.api.dto.TeacherDTO;
import com.ustu.erdbsystem.tasks.api.dtos.ResultDTO;
import com.ustu.erdbsystem.tasks.api.dtos.TaskDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
public class ResultWithTaskDTO extends ResultDTO {
    private TaskDTO taskDTO;
    private StudentDTO studentDTO;
    private TeacherDTO teacherDTO;
}

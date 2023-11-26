package com.ustu.erdbsystem.tasks.api.dtos.response;

import com.ustu.erdbsystem.ermodels.api.dto.ModelDTO;
import com.ustu.erdbsystem.persons.api.dto.StudentDTO;
import com.ustu.erdbsystem.persons.api.dto.TeacherDTO;
import com.ustu.erdbsystem.tasks.api.dtos.ResultDTO;
import com.ustu.erdbsystem.tasks.api.dtos.TaskDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResultWithTaskDTO {
    private ResultDTO resultDTO;
    private TaskDTO taskDTO;
    private ModelDTO modelSourceDTO;
    private ModelDTO modelResultDTO;
    private TeacherDTO teacherDTO;
    private StudentDTO studentDTO;
}

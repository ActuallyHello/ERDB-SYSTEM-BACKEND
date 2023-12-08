package com.ustu.erdbsystem.tasks.api.dtos.response;

import com.ustu.erdbsystem.ermodels.api.dto.response.ModelDetailDTO;
import com.ustu.erdbsystem.persons.api.dto.StudentDTO;
import com.ustu.erdbsystem.persons.api.dto.response.StudentWithGroupDTO;
import com.ustu.erdbsystem.persons.api.dto.response.TeacherWithPositionDTO;
import com.ustu.erdbsystem.tasks.api.dtos.ResultDTO;
import com.ustu.erdbsystem.tasks.api.dtos.TaskTitleDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
public class ResultWithModelDTO extends ResultDTO {
    private ModelDetailDTO modelDetailSourceDTO;
    private StudentDTO studentDTO;

    private ModelDetailDTO modelDetailResultDTO;
    private TeacherWithPositionDTO teacherWithPositionDTO;
    private TaskTitleDTO taskTitleDTO;
}

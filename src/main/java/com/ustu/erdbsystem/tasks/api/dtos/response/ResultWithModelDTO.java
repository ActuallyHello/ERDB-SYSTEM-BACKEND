package com.ustu.erdbsystem.tasks.api.dtos.response;

import com.ustu.erdbsystem.ermodels.api.dto.response.ModelDetailDTO;
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
public class ResultWithModelDTO extends ResultDTO {
    private ModelDetailDTO modelDetailSourceDTO;
    private ModelDetailDTO modelDetailResultDTO;
    private TaskDTO taskDTO;
    private TeacherDTO teacherDTO;
}

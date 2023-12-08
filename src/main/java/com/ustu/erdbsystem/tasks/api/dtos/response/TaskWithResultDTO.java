package com.ustu.erdbsystem.tasks.api.dtos.response;

import com.ustu.erdbsystem.ermodels.api.dto.ModelDTO;
import com.ustu.erdbsystem.persons.api.dto.StudentDTO;
import com.ustu.erdbsystem.persons.api.dto.TeacherDTO;
import com.ustu.erdbsystem.tasks.api.dtos.ResultDTO;
import com.ustu.erdbsystem.tasks.api.dtos.TaskDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class TaskWithResultDTO extends TaskDTO  {
    private TeacherDTO teacherDTO;
    private ResultDTO resultDTO;
}

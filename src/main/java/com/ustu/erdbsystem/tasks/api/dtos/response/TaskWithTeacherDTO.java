package com.ustu.erdbsystem.tasks.api.dtos.response;

import com.ustu.erdbsystem.persons.api.dto.TeacherDTO;
import com.ustu.erdbsystem.tasks.api.dtos.TaskDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class TaskWithTeacherDTO extends TaskDTO {
    private TeacherDTO teacherDTO;
}

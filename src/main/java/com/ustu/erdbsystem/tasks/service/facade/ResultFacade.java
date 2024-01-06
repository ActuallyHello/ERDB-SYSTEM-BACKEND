package com.ustu.erdbsystem.tasks.service.facade;

import com.ustu.erdbsystem.ermodels.api.dto.response.ModelDetailDTO;
import com.ustu.erdbsystem.ermodels.service.facade.ModelEntityRelationFacade;
import com.ustu.erdbsystem.ermodels.store.models.Model;
import com.ustu.erdbsystem.persons.api.dto.StudentDTO;
import com.ustu.erdbsystem.persons.api.dto.TeacherDTO;
import com.ustu.erdbsystem.persons.api.mapper.PersonDTOMapper;
import com.ustu.erdbsystem.persons.api.mapper.PositionDTOMapper;
import com.ustu.erdbsystem.persons.api.mapper.TeacherDTOMapper;
import com.ustu.erdbsystem.persons.service.StudentService;
import com.ustu.erdbsystem.persons.store.models.Person;
import com.ustu.erdbsystem.persons.store.models.Teacher;
import com.ustu.erdbsystem.tasks.api.dtos.TaskDTO;
import com.ustu.erdbsystem.tasks.api.dtos.response.ResultWithModelDTO;
import com.ustu.erdbsystem.tasks.api.dtos.response.ResultWithTaskDTO;
import com.ustu.erdbsystem.tasks.api.mapper.ResultWithModelDTOMapper;
import com.ustu.erdbsystem.tasks.api.mapper.ResultWithTaskDTOMapper;
import com.ustu.erdbsystem.tasks.api.mapper.TaskDTOMapper;
import com.ustu.erdbsystem.tasks.service.ResultService;
import com.ustu.erdbsystem.tasks.store.models.Result;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class ResultFacade {
    private final ResultService resultService;
    private final StudentService studentService;
    private final ModelEntityRelationFacade modelEntityRelationFacade;

    public List<ResultWithTaskDTO> getAllPreview(Pageable pageable) {
        List<Result> resultList = resultService.getAllWithTaskAndModelAndTeacher(pageable);
        return resultList.stream()
                .map(result -> {
                    TaskDTO taskDTO = TaskDTOMapper.makeDTO(result.getTask());
                    Person authorResult = result.getModel().getPerson();
                    StudentDTO studentDTO = studentService.getStudentDTOByPerson(authorResult);
                    TeacherDTO teacherDTO = result.getTeacher() != null
                            ? TeacherDTOMapper.makeDTO(
                                    result.getTeacher(),
                                    PersonDTOMapper.makeDTO(result.getTeacher().getPerson()),
                                    PositionDTOMapper.makeDTO(result.getTeacher().getPosition()))
                            : null;
                    return ResultWithTaskDTOMapper.makeDTO(result, taskDTO, studentDTO, teacherDTO);
                }).toList();
    }

    public ResultWithModelDTO getByIdToEvaluateResult(Result result) {
        Model sourceModelFromTask = result.getTask()
                .getDenormalizeModelList().get(0)
                .getModel();
        ModelDetailDTO sourceModelDetailDTO = modelEntityRelationFacade.getModelDetailDTOByModel(sourceModelFromTask);
        Model resultModelFromStudent = result.getModel();
        ModelDetailDTO resultModelDetailDTO = modelEntityRelationFacade.getModelDetailDTOByModel(resultModelFromStudent);
        TaskDTO taskDTO = TaskDTOMapper.makeDTO(result.getTask());

        Teacher teacher = result.getTeacher();
        TeacherDTO teacherDTO = teacher != null
                ? TeacherDTOMapper.makeDTO(
                        teacher,
                        PersonDTOMapper.makeDTO(teacher.getPerson()),
                        PositionDTOMapper.makeDTO(teacher.getPosition()))
                : null;
        return ResultWithModelDTOMapper.makeDTO(
                result,
                sourceModelDetailDTO,
                resultModelDetailDTO,
                taskDTO,
                teacherDTO);
    }

}

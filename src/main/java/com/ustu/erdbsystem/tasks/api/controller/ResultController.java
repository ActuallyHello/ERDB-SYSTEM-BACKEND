package com.ustu.erdbsystem.tasks.api.controller;

import com.ustu.erdbsystem.persons.exception.response.TeacherNotFoundException;
import com.ustu.erdbsystem.persons.service.TeacherService;
import com.ustu.erdbsystem.tasks.api.dtos.ResultDTO;
import com.ustu.erdbsystem.tasks.api.dtos.request.UpdateResultRequestDTO;
import com.ustu.erdbsystem.tasks.api.dtos.response.ResultWithModelDTO;
import com.ustu.erdbsystem.tasks.api.dtos.response.ResultWithTaskDTO;
import com.ustu.erdbsystem.tasks.api.mapper.ResultDTOMapper;
import com.ustu.erdbsystem.tasks.exception.response.ResultNotFoundException;
import com.ustu.erdbsystem.tasks.exception.response.ResultServerException;
import com.ustu.erdbsystem.tasks.exception.service.ResultCreationException;
import com.ustu.erdbsystem.tasks.service.ResultService;
import com.ustu.erdbsystem.tasks.service.facade.ResultFacade;
import com.ustu.erdbsystem.tasks.store.models.enums.Mark;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/results")
public class ResultController {

    private final ResultService resultService;
    private final TeacherService teacherService;

    private final ResultFacade resultFacade;

    private static final String BY_ID = "/{id}";

    @GetMapping
    public ResponseEntity<List<ResultWithTaskDTO>> getResultsWithTasks(@RequestParam(required = false) Integer page,
                                                                       @RequestParam(required = false) Integer size) {
        page = page == null ? 0 : page;
        size = size == null ? 20 : size;
        var pageable = PageRequest.of(page, size, Sort.by("updatedAt").descending());
        var resultWithTaskDTOList = resultFacade.getAllPreview(pageable);
        return ResponseEntity.ok(resultWithTaskDTOList);
    }

    @GetMapping(BY_ID)
    public ResponseEntity<ResultWithModelDTO> getResultById(@PathVariable Long id) {
        var result = resultService.getByIdWithModelAndTaskAndTeacher(id)
                .orElseThrow(() -> new ResultNotFoundException("Result with id=%d was not found".formatted(id)));
        var resultWithModelDTO = resultFacade.getByIdToEvaluateResult(result);
        return ResponseEntity.ok(resultWithModelDTO);
    }

    @PatchMapping(BY_ID)
    public ResponseEntity<ResultDTO> updateResultByTeacher(
            @RequestBody @Valid UpdateResultRequestDTO updateResultRequestDTO,
            @PathVariable Long id) {
        var result = resultService.getById(id)
                .orElseThrow(() -> new ResultNotFoundException(
                        "Result with id=%d was not found".formatted(id)));
        var teacher = teacherService.getByIdWithResults(updateResultRequestDTO.getTeacherId())
                .orElseThrow(() -> new TeacherNotFoundException(
                        "Teacher with id=%d was not found".formatted(updateResultRequestDTO.getTeacherId())));
        var mark = Mark.fromInteger(updateResultRequestDTO.getMark());
        try {
            result = resultService.update(result, mark, teacher);
            return ResponseEntity.ok(ResultDTOMapper.makeDTO(result));
        } catch (ResultCreationException exception) {
            throw new ResultServerException(exception.getMessage(), exception);
        }
    }
}

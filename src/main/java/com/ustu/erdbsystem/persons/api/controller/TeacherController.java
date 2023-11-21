package com.ustu.erdbsystem.persons.api.controller;

import com.ustu.erdbsystem.persons.api.dto.TeacherDTO;
import com.ustu.erdbsystem.persons.api.dto.response.TeacherWithPersonDTO;
import com.ustu.erdbsystem.persons.api.dto.response.TeacherWithPositionDTO;
import com.ustu.erdbsystem.persons.api.dto.request.CreateTeacherRequestDTO;
import com.ustu.erdbsystem.persons.api.mapper.PersonDTOMapper;
import com.ustu.erdbsystem.persons.api.mapper.PositionDTOMapper;
import com.ustu.erdbsystem.persons.api.mapper.TeacherDTOMapper;
import com.ustu.erdbsystem.persons.api.mapper.TeacherWithPersonDTOMapper;
import com.ustu.erdbsystem.persons.api.mapper.TeacherWithPositionDTOMapper;
import com.ustu.erdbsystem.persons.exception.response.PersonNotFoundException;
import com.ustu.erdbsystem.persons.exception.response.PositionServerException;
import com.ustu.erdbsystem.persons.exception.response.PositionNotFoundException;
import com.ustu.erdbsystem.persons.exception.response.TeacherNotFoundException;
import com.ustu.erdbsystem.persons.exception.service.PositionCreationException;
import com.ustu.erdbsystem.persons.exception.service.PositionDeleteException;
import com.ustu.erdbsystem.persons.service.PersonService;
import com.ustu.erdbsystem.persons.service.PositionService;
import com.ustu.erdbsystem.persons.service.TeacherService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/teaches")
public class TeacherController {

    private final TeacherService teacherService;
    private final PersonService personService;
    private final PositionService positionService;

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDTO> getTeacherById(@PathVariable Long id) {
        var teacher = teacherService.getByIdWithPersonAndPosition(id)
                .orElseThrow(() -> new TeacherNotFoundException("Teacher with id=%d was not found!".formatted(id)));
        var personDTO = PersonDTOMapper.makeDTO(teacher.getPerson());
        var positionDTO = PositionDTOMapper.makeDTO(teacher.getPosition());
        var teacherDTO = TeacherDTOMapper.makeDTO(teacher, personDTO, positionDTO);
        return ResponseEntity.ok(teacherDTO);
    }

    @GetMapping("/by-person/{personId}")
    public ResponseEntity<TeacherWithPositionDTO> getTeacherByPersonId(@PathVariable Long personId) {
        var teacher = teacherService.getByPersonIdWithPosition(personId)
                .orElseThrow(() -> new TeacherNotFoundException("Teacher with related Person(id=%d) was not found!".formatted(personId)));
        var positionDTO = PositionDTOMapper.makeDTO(teacher.getPosition());
        var teacherDTO = TeacherWithPositionDTOMapper.makeDTO(teacher, positionDTO);
        return ResponseEntity.ok(teacherDTO);
    }

    @GetMapping("/by-position/{positionId}")
    public ResponseEntity<List<TeacherWithPersonDTO>> getTeachersByPositionId(@PathVariable Long positionId) {
        var teacherDTOList = teacherService.getAllByPositionId(positionId).stream()
                .map(teacher -> TeacherWithPersonDTOMapper.makeDTO(
                        teacher,
                        PersonDTOMapper.makeDTO(teacher.getPerson())
                ))
                .toList();
        return ResponseEntity.ok(teacherDTOList);
    }

    @PostMapping
    public ResponseEntity<Object> createTeacher(@RequestBody @Valid CreateTeacherRequestDTO createTeacherRequestDTO) {
        var person = personService.getById(createTeacherRequestDTO.getPersonId())
                .orElseThrow(() -> new PersonNotFoundException("Person with id=%d was not found!".formatted(createTeacherRequestDTO.getPersonId())));
        var position = positionService.getById(createTeacherRequestDTO.getPositionId())
                .orElseThrow(() -> new PositionNotFoundException("Position with id=%d was not found!".formatted(createTeacherRequestDTO.getPositionId())));
        try {
            var teacher = teacherService.create(person, position);
            return ResponseEntity.ok(Map.of("teacherId", teacher.getId()));
        } catch (PositionCreationException exception) {
            throw new PositionServerException(exception.getMessage(), exception);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTeacherById(@PathVariable Long id) {
        var teacher = teacherService.getById(id)
                .orElseThrow(() -> new TeacherNotFoundException("Teacher with id=%d was not found!".formatted(id)));
        try {
            teacherService.delete(teacher);
            return ResponseEntity.noContent().build();
        } catch (PositionDeleteException exception) {
            throw new PositionServerException(exception.getMessage(), exception);
        }
    }
}

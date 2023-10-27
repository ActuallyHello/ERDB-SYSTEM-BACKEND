package com.ustu.erdbsystem.persons.api.controller;

import com.ustu.erdbsystem.persons.api.dto.StudentDTO;
import com.ustu.erdbsystem.persons.api.dto.StudentWithGroupDTO;
import com.ustu.erdbsystem.persons.api.dto.StudentWithPersonDTO;
import com.ustu.erdbsystem.persons.api.dto.request.CreateStudentRequestDTO;
import com.ustu.erdbsystem.persons.api.mapper.GroupDTOMapper;
import com.ustu.erdbsystem.persons.api.mapper.PersonDTOMapper;
import com.ustu.erdbsystem.persons.api.mapper.StudentDTOMapper;
import com.ustu.erdbsystem.persons.api.mapper.StudentWithGroupDTOMapper;
import com.ustu.erdbsystem.persons.api.mapper.StudentWithPersonDTOMapper;
import com.ustu.erdbsystem.persons.exception.response.GroupNotFoundException;
import com.ustu.erdbsystem.persons.exception.response.PersonNotFoundException;
import com.ustu.erdbsystem.persons.exception.response.StudentDBException;
import com.ustu.erdbsystem.persons.exception.response.StudentNotFoundException;
import com.ustu.erdbsystem.persons.exception.service.StudentCreationException;
import com.ustu.erdbsystem.persons.exception.service.StudentDeleteException;
import com.ustu.erdbsystem.persons.service.GroupService;
import com.ustu.erdbsystem.persons.service.PersonService;
import com.ustu.erdbsystem.persons.service.StudentService;
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

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;
    private final GroupService groupService;
    private final PersonService personService;

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
        var student = studentService.getByIdWithPersonAndGroup(id)
                .orElseThrow(() -> new StudentNotFoundException("Student with id=%d was not found!".formatted(id)));
        var groupDTO = GroupDTOMapper.makeDTO(student.getGroup());
        var personDTO = PersonDTOMapper.makeDTO(student.getPerson());
        var studentDTO = StudentDTOMapper.makeDTO(groupDTO, personDTO);
        return ResponseEntity.ok(studentDTO);
    }

    @GetMapping("/by-person/{personId}")
    public ResponseEntity<StudentWithGroupDTO> getStudentByPersonId(@PathVariable Long personId) {
        var student = studentService.getByPersonIdWithGroup(personId)
                .orElseThrow(() -> new StudentNotFoundException("Student with related Person(id=%d) was not found!".formatted(personId)));
        var groupDTO = GroupDTOMapper.makeDTO(student.getGroup());
        var studentWithGroupDTO = StudentWithGroupDTOMapper.makeDTO(student, groupDTO);
        return ResponseEntity.ok(studentWithGroupDTO);
    }

    @GetMapping("/by-group/{groupId}")
    public ResponseEntity<List<StudentWithPersonDTO>> getStudentsByGroupId(@PathVariable Long groupId) {
        var studentDTOList = studentService.getAllByGroupIdWithPerson(groupId).stream()
                .map(student -> StudentWithPersonDTOMapper.makeDTO(
                        student,
                        PersonDTOMapper.makeDTO(student.getPerson()))
                )
                .toList();
        return ResponseEntity.ok(studentDTOList);
    }

    @PostMapping("")
    public ResponseEntity<Long> createStudent(@RequestBody CreateStudentRequestDTO createStudentRequestDTO) {
        var group = groupService.getById(createStudentRequestDTO.getGroupId())
                .orElseThrow(() -> new GroupNotFoundException("Group with id=%d was not found!".formatted(createStudentRequestDTO.getGroupId())));
        var person = personService.getById(createStudentRequestDTO.getPersonId())
                .orElseThrow(() -> new PersonNotFoundException("Person with id=%d was not found!".formatted(createStudentRequestDTO.getPersonId())));
        try {
            var student = studentService.create(person, group);
            return ResponseEntity.ok(student.getId());
        } catch (StudentCreationException exception) {
            throw new StudentDBException("Error when creating student! " + exception.getMessage(), exception);
        }
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<Object> deleteStudentById(@PathVariable Long id) {
        var student = studentService.getById(id)
                .orElseThrow(() -> new StudentNotFoundException("Student with id=%d was not found!".formatted(id)));
        try {
            studentService.delete(student);
            return ResponseEntity.noContent().build();
        } catch (StudentDeleteException exception) {
            throw new StudentDBException("Error when deleting student! " + exception.getMessage(), exception);
        }
    }
}

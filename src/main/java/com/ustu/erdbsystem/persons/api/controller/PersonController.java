package com.ustu.erdbsystem.persons.api.controller;

import com.ustu.erdbsystem.ermodels.exception.validation.EnumValueException;
import com.ustu.erdbsystem.persons.api.dto.PersonDTO;
import com.ustu.erdbsystem.persons.api.dto.request.CreatePersonRequestDTO;
import com.ustu.erdbsystem.persons.api.mapper.PersonDTOMapper;
import com.ustu.erdbsystem.persons.exception.response.PersonDBException;
import com.ustu.erdbsystem.persons.exception.response.PersonNotFoundException;
import com.ustu.erdbsystem.persons.exception.response.UserNotFoundException;
import com.ustu.erdbsystem.persons.exception.service.PersonCreationException;
import com.ustu.erdbsystem.persons.exception.service.PersonDeleteException;
import com.ustu.erdbsystem.persons.exception.validation.PersonValidationException;
import com.ustu.erdbsystem.persons.service.PersonService;
import com.ustu.erdbsystem.persons.service.UserService;
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
@RequestMapping("/persons")
public class PersonController {

    private final PersonService personService;
    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<List<PersonDTO>> getPersons() {
        var personList = personService.getAll().stream()
                .map(PersonDTOMapper::makeDTO)
                .toList();
        return ResponseEntity.ok(personList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable Long id) {
        var person = personService.getById(id)
                .map(PersonDTOMapper::makeDTO)
                .orElseThrow(() -> new PersonNotFoundException("Person with id=%d was not found!".formatted(id)));
        return ResponseEntity.ok(person);
    }

    @PostMapping("")
    public ResponseEntity<Object> createPerson(@RequestBody CreatePersonRequestDTO createPersonRequestDTO) {
        var user = userService.getById(createPersonRequestDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User with id=%d was not found!".formatted(createPersonRequestDTO.getUserId())));
        PersonDTO personDTO;
        try {
            personDTO = PersonDTOMapper.makeDTO(createPersonRequestDTO);
        } catch (EnumValueException exception) {
            throw new PersonValidationException("Validation error! " + exception.getMessage(), exception);
        }
        try {
            var person = personService.create(personDTO, user);
            return ResponseEntity.ok(Map.of("personId", person.getId()));
        } catch (PersonCreationException exception) {
            throw new PersonDBException("Error when creating person! " + exception.getMessage(), exception);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletePersonById(@PathVariable Long id) {
        var person = personService.getById(id)
                .orElseThrow(() -> new PersonNotFoundException("Person with id=%d was not found!".formatted(id)));
        try {
            personService.delete(person);
            return ResponseEntity.noContent().build();
        } catch (PersonDeleteException exception) {
            throw new PersonDBException("Error when deleting person! " + exception.getMessage(), exception);
        }
    }
}

package com.ustu.erdbsystem.persons.api.controller;

import com.ustu.erdbsystem.ermodels.exception.validation.EnumValueException;
import com.ustu.erdbsystem.persons.api.dto.PersonDTO;
import com.ustu.erdbsystem.persons.api.dto.request.CreatePersonRequestDTO;
import com.ustu.erdbsystem.persons.api.mapper.PersonDTOMapper;
import com.ustu.erdbsystem.persons.exception.response.PersonServerException;
import com.ustu.erdbsystem.persons.exception.response.PersonNotFoundException;
import com.ustu.erdbsystem.persons.exception.response.UserNotFoundException;
import com.ustu.erdbsystem.persons.exception.service.PersonCreationException;
import com.ustu.erdbsystem.persons.exception.service.PersonDeleteException;
import com.ustu.erdbsystem.persons.exception.validation.PersonValidationException;
import com.ustu.erdbsystem.persons.service.PersonService;
import com.ustu.erdbsystem.persons.service.UserService;
import com.ustu.erdbsystem.persons.store.models.Person;
import com.ustu.erdbsystem.persons.store.models.enums.PersonType;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonService personService;
    private final UserService userService;

    @GetMapping("/")
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

    @PostMapping("/")
    public ResponseEntity<Object> createPerson(@RequestBody @Valid CreatePersonRequestDTO createPersonRequestDTO) {
        var user = userService.getById(createPersonRequestDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User with id=%d was not found!".formatted(createPersonRequestDTO.getUserId())));

        System.out.println(user);

        PersonDTO personDTO;
        try {
            personDTO = PersonDTOMapper.makeDTO(createPersonRequestDTO);
        } catch (EnumValueException exception) {
            throw new PersonValidationException(exception.getMessage(), exception);
        }
        System.out.println(personDTO);
        try {
            var person = personService.create(personDTO, user);
            System.out.println(person);
            return ResponseEntity.ok(Map.of("personId", person.getId()));
        } catch (PersonCreationException exception) {
            throw new PersonServerException(exception.getMessage(), exception);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePersonById(@PathVariable Long id) {
        var person = personService.getById(id)
                .orElseThrow(() -> new PersonNotFoundException("Person with id=%d was not found!".formatted(id)));
        try {
            personService.delete(person);
            return ResponseEntity.noContent().build();
        } catch (PersonDeleteException exception) {
            throw new PersonServerException(exception.getMessage(), exception);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonDTO> updatePersonById(@PathVariable Long id,
                                                   @RequestBody @Valid CreatePersonRequestDTO createPersonRequestDTO) {
        var person = personService.getById(id)
                .orElseThrow(() -> new PersonNotFoundException("Person with id=%d was not found!".formatted(id)));
        if (!Objects.equals(person.getUser().getId(), createPersonRequestDTO.getUserId())) {
            var user = userService.getById(createPersonRequestDTO.getUserId())
                    .orElseThrow(() -> new UserNotFoundException("User with id=%d was not found!".formatted(createPersonRequestDTO.getUserId())));
            person.setUser(user);
        }
        try {
            var personType = PersonType.fromString(createPersonRequestDTO.getPersonType());
            person.setPersonType(personType);
        } catch (EnumValueException exception) {
            throw new PersonValidationException(exception.getMessage(), exception);
        }
        person.setFirstName(createPersonRequestDTO.getFirstName());
        person.setLastName(createPersonRequestDTO.getLastName());
        person.setMiddleName(createPersonRequestDTO.getMiddleName());
        try {
            person = personService.update(person);
        } catch (PersonDeleteException exception) {
            throw new PersonServerException(exception.getMessage(), exception);
        }
        var personDTO = PersonDTOMapper.makeDTO(person);
        return ResponseEntity.ok(personDTO);
    }
}

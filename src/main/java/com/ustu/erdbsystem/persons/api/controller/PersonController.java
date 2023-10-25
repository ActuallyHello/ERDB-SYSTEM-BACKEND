package com.ustu.erdbsystem.persons.api.controller;

import com.ustu.erdbsystem.persons.api.dto.PersonDTO;
import com.ustu.erdbsystem.persons.api.mapper.PersonDTOMapper;
import com.ustu.erdbsystem.persons.exception.response.PersonNotFoundException;
import com.ustu.erdbsystem.persons.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/persons")
public class PersonController {

    private PersonService personService;

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> getPersonById(@PathVariable Long id) {
        var person = personService.getById(id)
                .map(PersonDTOMapper::makeDTO)
                .orElseThrow(() -> new PersonNotFoundException("Person with id=%d was not found!".formatted(id)));
        return ResponseEntity.ok(person);
    }
}

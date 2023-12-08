package com.ustu.erdbsystem.persons.service.impl;

import com.ustu.erdbsystem.persons.api.dto.PersonDTO;
import com.ustu.erdbsystem.persons.api.dto.request.CreatePersonRequestDTO;
import com.ustu.erdbsystem.persons.api.mapper.PersonDTOMapper;
import com.ustu.erdbsystem.persons.exception.response.UserNotFoundException;
import com.ustu.erdbsystem.persons.exception.service.PersonCreationException;
import com.ustu.erdbsystem.persons.exception.service.PersonDeleteException;
import com.ustu.erdbsystem.persons.service.PersonService;
import com.ustu.erdbsystem.persons.service.UserService;
import com.ustu.erdbsystem.persons.store.models.Person;
import com.ustu.erdbsystem.persons.store.models.User;
import com.ustu.erdbsystem.persons.store.models.enums.PersonType;
import com.ustu.erdbsystem.persons.store.repos.PersonRepo;
import jakarta.persistence.PersistenceException;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class PersonServiceImpl implements PersonService {

    private PersonRepo personRepo;

    @Override
    public List<Person> getAll() {
        var personList = personRepo.findAll();
        log.info("GET PERSONS ({})", personList.size());
        return personList;
    }

    @Override
    public Optional<Person> getById(Long id) {
        var person = personRepo.findById(id);
        log.info("GET PERSON BY ID={}", id);
        return person;
    }

    @Override
    public Optional<Person> getByIdWithModels(Long id) {
        System.out.println();
        var person = personRepo.findByIdWithModels(id);
        log.info("GET PERSON BY ID={}", id);
        return person;
    }

    @Override
    public Optional<Person> getByUser(User user) {
        var person = personRepo.findByUser(user);
        log.info("GET PERSON BY USER WITH ID={}", user.getId());
        return person;
    }

    @Override
    @Transactional
    public Person create(PersonDTO personDTO, User user) {
        var person = PersonDTOMapper.fromDTO(personDTO);
        person.setUser(user);
        try {
            person = personRepo.saveAndFlush(person);
            log.info("CREATED PERSON WITH ID={}", person.getId());
            return person;
        } catch (DataIntegrityViolationException | PersistenceException exception) {
            log.error("ERROR WHEN CREATING PERSON: {}", exception.getMessage());
            throw new PersonCreationException("Error when creating person! [DatabaseException]", exception);
        }
    }

    @Override
    @Transactional
    public void delete(Person person) {
        try {
            personRepo.delete(person);
            personRepo.flush();
            log.info("PERSON WITH ID={} WAS DELETED", person.getId());
        } catch (DataIntegrityViolationException | PersistenceException exception) {
            log.error("ERROR WHEN DELETING PERSON! {}", exception.getMessage());
            throw new PersonDeleteException("Error when deleting person! [DatabaseException]", exception);
        }
    }

    @Override
    @Transactional
    public Person update(Person person, User user, PersonDTO personDTO) {
        if (!Objects.equals(person.getUser().getId(), user.getId())) {
            person.setUser(user);
        }
        person.setPersonType(personDTO.getPersonType());
        person.setFirstName(personDTO.getFirstName());
        person.setLastName(personDTO.getLastName());
        person.setMiddleName(personDTO.getMiddleName());
        try {
            person = personRepo.saveAndFlush(person);
            log.info("PERSON WITH ID={} WAS UPDATED", person.getId());
            return person;
        } catch (DataIntegrityViolationException exception) {
            log.error("ERROR WHEN UPDATING PERSON! {}", exception.getMessage());
            throw new PersonCreationException("Error when updating person! [DatabaseException]", exception);
        }
    }
}

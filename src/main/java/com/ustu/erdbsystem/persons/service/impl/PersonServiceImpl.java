package com.ustu.erdbsystem.persons.service.impl;

import com.ustu.erdbsystem.persons.api.dto.PersonDTO;
import com.ustu.erdbsystem.persons.api.mapper.PersonDTOMapper;
import com.ustu.erdbsystem.persons.exception.service.PersonCreationException;
import com.ustu.erdbsystem.persons.exception.service.PersonDeleteException;
import com.ustu.erdbsystem.persons.service.PersonService;
import com.ustu.erdbsystem.persons.store.models.Person;
import com.ustu.erdbsystem.persons.store.models.User;
import com.ustu.erdbsystem.persons.store.repos.PersonRepo;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
public class PersonServiceImpl implements PersonService {

    private PersonRepo personRepo;

    @Override
    @Transactional
    public Optional<Person> getById(Long id) {
        var person = personRepo.findById(id);
        log.info("GET MODEL BY ID={}", id);
        return person;
    }

    @Override
    @Transactional
    public Optional<Person> getByUser(User user) {
        var person = personRepo.findByUser(user);
        log.info("GET PERSON BY USER WITH ID={}", user.getId());
        return person;
    }

    @Override
    @Transactional
    public Person create(PersonDTO personDTO, User user) {
        var person = PersonDTOMapper.fromDTO(personDTO);
        try {
            person = personRepo.saveAndFlush(person);
            log.info("CREATED PERSON WITH ID={}", person.getId());
            return person;
        } catch (PersistenceException exception) {
            throw new PersonCreationException(exception.getMessage(), exception);
        }
    }

    @Override
    @Transactional
    public void delete(Person person) {
        try {
            personRepo.delete(person);
            log.info("PERSON WITH ID={} WAS DELETED", person.getId());
        } catch (PersistenceException exception) {
            log.error("CANNOT DELETE PERSON WITH ID={}! {}", person.getId(), exception.getMessage());
            throw new PersonDeleteException(exception.getMessage(), exception);
        }
    }

    @Override
    @Transactional
    public Person update(Person personNew) {
        try {
            var person = personRepo.saveAndFlush(personNew);
            log.info("PERSON WITH ID={} WAS UPDATED", person.getId());
            return person;
        } catch (PersistenceException exception) {
            log.error("CANNOT UPDATE PERSON! {}", exception.getMessage());
            throw new PersonCreationException(exception.getMessage(), exception);
        }
    }
}

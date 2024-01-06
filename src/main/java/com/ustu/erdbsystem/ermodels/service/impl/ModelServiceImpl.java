package com.ustu.erdbsystem.ermodels.service.impl;

import com.ustu.erdbsystem.ermodels.api.dto.ModelDTO;
import com.ustu.erdbsystem.ermodels.api.mapper.ModelDTOMapper;
import com.ustu.erdbsystem.ermodels.exception.service.ModelCreationException;
import com.ustu.erdbsystem.ermodels.exception.service.ModelDeleteException;
import com.ustu.erdbsystem.ermodels.service.ModelService;
import com.ustu.erdbsystem.ermodels.store.models.Model;
import com.ustu.erdbsystem.ermodels.store.repos.ModelRepo;
import com.ustu.erdbsystem.persons.store.models.Person;
import jakarta.persistence.PersistenceException;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class ModelServiceImpl implements ModelService {

    private final ModelRepo modelRepo;

    @Override
    public List<Model> getAll(Pageable pageable, Boolean includeStudents, Boolean includeTaskResults) {
        List<Model> modelList;
        if (includeStudents) {
            modelList = modelRepo.findAllByIsTaskResult(includeTaskResults, pageable);
        } else {
            modelList = modelRepo.findAllByTeachers(pageable);
        }
        log.debug("GET MODELS ({}) PAGE={} SIZE={}", modelList.size(), pageable.getPageNumber(), pageable.getPageSize());
        return modelList;
    }

    @Override
    public Optional<Model> getById(Long id) {
        var model = modelRepo.findById(id);
        log.debug("GET MODEL WITH ID={}", id);
        return model;
    }


    @Override
    public List<Model> getAllByPerson(Person person) {
        var modelList = modelRepo.findAllByPerson(person);
        log.debug("GET MODELS ({}) BY PERSON WITH ID={}", modelList.size(), person.getId());
        return modelList;
    }

    @Override
    @Transactional
    public void deleteModel(Model model) {
        try {
            modelRepo.delete(model);
            modelRepo.flush();
            log.info("MODEL WITH ID={} WAS DELETED!", model.getId());
        } catch (DataIntegrityViolationException | PersistenceException exception) {
            log.error("ERROR WHEN DELETING MODEL WITH ID={}! {}", model.getId(), exception.getMessage());
            throw new ModelDeleteException("Error when deleting model! [DatabaseException]", exception);
        }
    }

    @Override
    @Transactional
    public Model create(ModelDTO modelDTO, Person person) {
        var model = ModelDTOMapper.fromDTO(modelDTO);
        person.addModel(model);
        try {
            model = modelRepo.saveAndFlush(model);
            log.info("MODEL WITH ID={} WAS CREATED", model.getId());
            return model;
        } catch (DataIntegrityViolationException | PersistenceException exception) {
            log.error("ERROR WHEN CREATING MODEL! {}", exception.getMessage());
            throw new ModelCreationException("Error when creating model! [DatabaseException]", exception);
        }
    }
}

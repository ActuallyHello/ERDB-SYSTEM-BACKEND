package com.ustu.erdbsystem.ermodels.service.impl;

import com.ustu.erdbsystem.ermodels.api.dto.ModelDTO;
import com.ustu.erdbsystem.ermodels.api.dto.ModelEntityDTO;
import com.ustu.erdbsystem.ermodels.api.dto.RelationDTO;
import com.ustu.erdbsystem.ermodels.api.mapper.ModelDTOMapper;
import com.ustu.erdbsystem.ermodels.exception.service.ModelCreationException;
import com.ustu.erdbsystem.ermodels.exception.service.ModelDeleteException;
import com.ustu.erdbsystem.ermodels.exception.service.ModelEntityCreationException;
import com.ustu.erdbsystem.ermodels.exception.service.RelationCreationException;
import com.ustu.erdbsystem.ermodels.exception.service.RelationDeleteException;
import com.ustu.erdbsystem.ermodels.exception.validation.RelationDoesNotMatchEntityException;
import com.ustu.erdbsystem.ermodels.service.ModelEntityAttributeService;
import com.ustu.erdbsystem.ermodels.service.ModelService;
import com.ustu.erdbsystem.ermodels.service.RelationService;
import com.ustu.erdbsystem.ermodels.store.models.Model;
import com.ustu.erdbsystem.ermodels.store.models.ModelEntity;
import com.ustu.erdbsystem.ermodels.store.repos.ModelRepo;
import com.ustu.erdbsystem.persons.store.models.Person;
import jakarta.persistence.PersistenceException;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class ModelServiceImpl implements ModelService {

    private ModelEntityAttributeService modelEntityAttributeService;
    private RelationService relationService;
    private ModelRepo modelRepo;

    @Override
    public List<Model> getAllWithPerson() {
        var modelList = modelRepo.findAll();
        log.info("GET MODELS ({})", modelList.size());
        return modelList;
    }

    @Override
    public List<Model> getAllWithPerson(List<Long> idList) {
        var modelList = modelRepo.findAllById(idList);
        log.info("GET MODELS ({})", modelList.size());
        return modelList;
    }

    @Override
    public List<Model> getAllWithPerson(int page, int size) {
        var pageable = PageRequest.of(page, size, Sort.by("id", "title"));
        var modelList = modelRepo.findAllBy(pageable);
        log.info("GET MODELS ({}) PAGE={} SIZE={}", modelList.size(), page, size);
        return modelList;
    }

    @Override
    public Optional<Model> getById(Long id) {
        var model = modelRepo.findById(id);
        log.info("GET MODEL WITH ID={}", id);
        return model;
    }

    @Override
    public List<Model> getAllByPerson(Person person) {
        var modelList = modelRepo.findAllByPerson(person);
        log.info("GET MODELS ({}) BY PERSON WITH ID={}", modelList.size(), person.getId());
        return modelList;
    }

    @Override
    @Transactional
    public void deleteModel(Model model) {
        try {
            relationService.deleteRelationsFromModel(model);
        } catch (RelationDeleteException exception) {
            throw new ModelDeleteException(exception.getMessage(), exception);
        }
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
    public Model create(Person person,
                        ModelDTO modelDTO,
                        List<ModelEntityDTO> modelEntityDTOList,
                        List<RelationDTO> relationDTOList) {
        var model = ModelDTOMapper.fromDTO(modelDTO);
        person.addModel(model);
        try {
            model = modelRepo.saveAndFlush(model);
            log.info("MODEL WITH ID={} WAS CREATED", model.getId());
        } catch (DataIntegrityViolationException | PersistenceException exception) {
            log.error("ERROR WHEN CREATING MODEL! {}", exception.getMessage());
            throw new ModelCreationException("Error when creating model! [DatabaseException]", exception);
        }
        List<ModelEntity> modelEntityList;
        try {
            modelEntityList = modelEntityAttributeService.createModelEntities(modelEntityDTOList, model);
        } catch (ModelEntityCreationException exception) {
            throw new ModelCreationException(exception.getMessage(), exception);
        }
        try {
            relationService.createEntitiesRelations(relationDTOList, modelEntityList);
        } catch (RelationCreationException | RelationDoesNotMatchEntityException exception) {
            throw new ModelCreationException(exception.getMessage(), exception);
        }
        return model;
    }
}

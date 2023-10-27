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
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
public class ModelServiceImpl implements ModelService {

    private ModelEntityAttributeService modelEntityAttributeService;
    private RelationService relationService;
    private ModelRepo modelRepo;

    @Override
    @Transactional
    public List<Model> getAll() {
        var modelList = modelRepo.findAll();
        log.info("GET MODELS (%d)".formatted(modelList.size()));
        return modelList;
    }

    @Override
    @Transactional
    public List<Model> getAll(List<Long> idList) {
        var modelList = modelRepo.findByIdIn(idList);
        log.info("GET MODELS (%d)".formatted(modelList.size()));
        return modelList;
    }

    @Override
    @Transactional
    public Optional<Model> getById(Long id) {
        var model = modelRepo.findById(id);
        log.info("GET MODEL WITH ID=%d".formatted(id));
        return model;
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
            log.info("MODEL WITH ID=%d WAS DELETED!".formatted(model.getId()));
        } catch (PersistenceException exception) {
            log.error("CANNOT DELETE MODEL WITH ID=%d! %s".formatted(model.getId(), exception));
            throw new ModelDeleteException(exception.getMessage(), exception);
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
            log.info("MODEL WITH ID=%d WAS CREATED");
        } catch (PersistenceException exception) {
            log.error("ERROR WHEN CREATING A MODELS: %s".formatted(exception.getMessage()));
            throw new ModelCreationException(exception.getMessage(), exception);
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

package com.ustu.erdbsystem.ermodels.service.impl;

import com.ustu.erdbsystem.ermodels.api.dto.ModelDTO;
import com.ustu.erdbsystem.ermodels.api.dto.ModelEntityDTO;
import com.ustu.erdbsystem.ermodels.api.dto.RelationDTO;
import com.ustu.erdbsystem.ermodels.api.mapper.ModelDTOMapper;
import com.ustu.erdbsystem.ermodels.exception.service.ModelCreationException;
import com.ustu.erdbsystem.ermodels.exception.service.ModelDeleteException;
import com.ustu.erdbsystem.ermodels.exception.validation.RelationDoesNotMatchEntityException;
import com.ustu.erdbsystem.ermodels.service.ModelEntityAttributeService;
import com.ustu.erdbsystem.ermodels.service.ModelService;
import com.ustu.erdbsystem.ermodels.service.RelationService;
import com.ustu.erdbsystem.ermodels.store.models.Model;
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
        log.info("GET %d MODELS".formatted(modelList.size()));
        return modelList;
    }

    @Override
    @Transactional
    public List<Model> getAll(List<Long> idList) {
        var modelList = modelRepo.findByIdIn(idList);
        log.info("GET %d MODELS".formatted(modelList.size()));
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
        if (model == null) throw new IllegalArgumentException("model is null!");
        if (model.getId() == null) throw new IllegalArgumentException("model id is null!");
        try {
            relationService.deleteRelationsFromModel(model);
            modelRepo.delete(model);
            log.info("MODEL WITH ID=%d WAS DELETED!".formatted(model.getId()));
        } catch (RelationDoesNotMatchEntityException | PersistenceException exception) {
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
        try {
            Model model = ModelDTOMapper.fromDTO(modelDTO);
            person.addModel(model);
            model = modelRepo.saveAndFlush(model);
            log.info("MODEL WITH ID=%d WAS CREATED");
            var modelEntityList = modelEntityAttributeService.createModelEntities(modelEntityDTOList, model);
            relationService.createEntitiesRelations(relationDTOList, modelEntityList);
            return model;
        } catch (PersistenceException | RelationDoesNotMatchEntityException exception) {
            log.error("ERROR WHEN CREATING A MODEL: %s".formatted(exception.getMessage()));
            throw new ModelCreationException(exception.getMessage(), exception);
        }
    }
}

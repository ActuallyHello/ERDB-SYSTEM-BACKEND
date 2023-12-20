package com.ustu.erdbsystem.ermodels.service.impl;

import com.ustu.erdbsystem.ermodels.api.dto.ModelDTO;
import com.ustu.erdbsystem.ermodels.api.dto.ModelEntityDTO;
import com.ustu.erdbsystem.ermodels.api.dto.RelationDTO;
import com.ustu.erdbsystem.ermodels.api.dto.response.ModelDetailDTO;
import com.ustu.erdbsystem.ermodels.api.mapper.AttributeDTOMapper;
import com.ustu.erdbsystem.ermodels.api.mapper.ModelDTOMapper;
import com.ustu.erdbsystem.ermodels.api.mapper.ModelDetailDTOMapper;
import com.ustu.erdbsystem.ermodels.api.mapper.ModelEntityDTOMapper;
import com.ustu.erdbsystem.ermodels.api.mapper.RelationDTOMapper;
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
import com.ustu.erdbsystem.ermodels.store.repos.ModelRepo;
import com.ustu.erdbsystem.persons.api.mapper.PersonDTOMapper;
import com.ustu.erdbsystem.persons.service.StudentService;
import com.ustu.erdbsystem.persons.service.TeacherService;
import com.ustu.erdbsystem.persons.store.models.Person;
import com.ustu.erdbsystem.persons.store.models.enums.PersonType;
import jakarta.persistence.PersistenceException;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class ModelServiceImpl implements ModelService {

    private final RelationService relationService;
    private final ModelRepo modelRepo;
    private final ModelEntityAttributeService modelEntityAttributeService;

    @Override
    public List<Model> getAll(Integer page, Integer size, Boolean includeStudents, Boolean includeTaskResults) {
        var pageable = PageRequest.of(page, size, Sort.by("id", "title"));
        List<Model> modelList;
        if (includeStudents) {
            modelList = modelRepo.findAllByIsTaskResult(includeTaskResults, pageable);
        } else {
            modelList = modelRepo.findAllByTeachers(pageable);
        }
        log.debug("GET MODELS ({}) PAGE={} SIZE={}", modelList.size(), page, size);
        return modelList;
    }

    @Override
    public Optional<Model> getById(Long id) {
        var model = modelRepo.findById(id);
        log.debug("GET MODEL WITH ID={}", id);
        return model;
    }

    @Override
    public ModelDetailDTO getModelDetailDTOByModel(Model model) {
        var personDTO = PersonDTOMapper.makeDTO(model.getPerson());
        var modelEntityDTOList = modelEntityAttributeService.getAllByModel(model).stream()
                .map(modelEntity -> {
                    var attributeDTOList = modelEntity.getAttributeList().stream()
                            .map(AttributeDTOMapper::makeDTO)
                            .toList();
                    return ModelEntityDTOMapper.makeDTO(modelEntity, attributeDTOList);
                }).toList();
        var modelEntityIdList = modelEntityDTOList.stream()
                .map(ModelEntityDTO::getId)
                .toList();
        var relationDTOList = relationService.getRelationsByEntityIds(modelEntityIdList).stream()
                .map(RelationDTOMapper::makeDTO)
                .toList();
        return ModelDetailDTOMapper.makeDTO(model, personDTO, modelEntityDTOList, relationDTOList);
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
    public Model create(ModelDTO modelDTO,
                        List<ModelEntityDTO> modelEntityDTOList,
                        List<RelationDTO> relationDTOList,
                        Person person) {
        var model = ModelDTOMapper.fromDTO(modelDTO);
        person.addModel(model);
        try {
            model = modelRepo.saveAndFlush(model);
            log.info("MODEL WITH ID={} WAS CREATED", model.getId());
            var modelEntityList = modelEntityAttributeService.createModelEntities(modelEntityDTOList, model);
            relationService.createEntitiesRelations(relationDTOList, modelEntityList);
        } catch (ModelEntityCreationException | RelationCreationException |
                 RelationDoesNotMatchEntityException | DataIntegrityViolationException |
                 PersistenceException exception) {
            log.error("ERROR WHEN CREATING MODEL! {}", exception.getMessage());
            throw new ModelCreationException("Error when creating model! [DatabaseException]", exception);
        }
        return model;
    }
}

package com.ustu.erdbsystem.ermodels.service.impl;

import com.ustu.erdbsystem.ermodels.api.dto.ModelDTO;
import com.ustu.erdbsystem.ermodels.api.dto.ModelEntityDTO;
import com.ustu.erdbsystem.ermodels.api.dto.RelationDTO;
import com.ustu.erdbsystem.ermodels.exception.EnumValueException;
import com.ustu.erdbsystem.ermodels.exception.RelationDoesNotMatchEntityException;
import com.ustu.erdbsystem.ermodels.exception.RequestDataValidationException;
import com.ustu.erdbsystem.ermodels.service.ModelService;
import com.ustu.erdbsystem.ermodels.store.models.Attribute;
import com.ustu.erdbsystem.ermodels.store.models.Model;
import com.ustu.erdbsystem.ermodels.store.models.ModelEntity;
import com.ustu.erdbsystem.ermodels.store.models.Relation;
import com.ustu.erdbsystem.ermodels.store.models.enums.AttributeType;
import com.ustu.erdbsystem.ermodels.store.models.enums.Power;
import com.ustu.erdbsystem.ermodels.store.repos.AttributeRepo;
import com.ustu.erdbsystem.ermodels.store.repos.ModelEntityRepo;
import com.ustu.erdbsystem.ermodels.store.repos.ModelRepo;
import com.ustu.erdbsystem.ermodels.store.repos.RelationRepo;
import com.ustu.erdbsystem.persons.store.models.Person;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
public class ModelServiceImpl implements ModelService {

    private ModelRepo modelRepo;
    private RelationRepo relationRepo;
    private ModelEntityRepo modelEntityRepo;

    @Override
    @Transactional
    public List<Model> getAll() {
        return modelRepo.findAll().stream().toList();
    }

    @Override
    @Transactional
    public List<Model> getAll(List<Long> idList) {
        return modelRepo.findByIdIn(idList).stream().toList();
    }

    @Override
    @Transactional
    public Optional<Model> getById(Long id) {
        return modelRepo.findById(id);
    }

    @Override
    @Transactional
    public List<Relation> getRelationsByEntityIds(List<Long> modelEntityIdList) {
        return relationRepo.findByModelEntity1IdOrModelEntity2IdInModelEntityIdList(modelEntityIdList);
    }

    @Override
    @Transactional
    public void deleteModel(Model model) {
        var modelEntityList = model.getModelEntityList();
        if (!modelEntityList.isEmpty()) {
            var relationList = getRelationsByEntityIds(modelEntityList.stream()
                    .map(ModelEntity::getId)
                    .toList()
            );
            relationRepo.deleteAll(relationList);
        }
        modelRepo.delete(model);
    }

    /**
     * CREATE A MODEL WITH ENTITIES AND THEIR ATTRIBUTES
     * Create model object, then iterate through model entities
     *  and then iterate through entity attributes
     * @param person - author
     * @return - model's id
     */
    @Override
    @Transactional
    public Long create(Person person,
                       ModelDTO modelDTO,
                       List<ModelEntityDTO> modelEntityDTOList,
                       List<RelationDTO> relationDTOList) throws RelationDoesNotMatchEntityException, EnumValueException {
        Model model = Model.builder()
                .person(person)
                .title(modelDTO.getTitle())
                .description(modelDTO.getDescription())
                .topic(modelDTO.getTopic())
                .isTaskResult(modelDTO.getIsTaskResult())
                .build();
        model = modelRepo.saveAndFlush(model);
        var modelEntityList = collectEntitiesWithAttributesToModel(modelEntityDTOList, model);
        modelEntityRepo.saveAll(modelEntityList);
        var relationList = collectRelationsToEntities(relationDTOList, modelEntityList);
        relationRepo.saveAll(relationList);
        return model.getId();
    }

    private List<ModelEntity> collectEntitiesWithAttributesToModel(List<ModelEntityDTO> modelEntityDTOList, Model model) {
        for (var modelEntityDTO : modelEntityDTOList) {
            ModelEntity modelEntity = ModelEntity.builder()
                    .title(modelEntityDTO.getTitle())
                    .model(model)
                    .build();
            for (var attributeDTO : modelEntityDTO.getAttributeDTOList()) {
                Attribute attribute = Attribute.builder()
                        .title(attributeDTO.getTitle())
                        .attributeType(AttributeType.fromString(attributeDTO.getAttributeType()))
                        .modelEntity(modelEntity)
                        .build();
                modelEntity.getAttributeList().add(attribute);
            }
            model.getModelEntityList().add(modelEntity);
        }
        return model.getModelEntityList();
    }

    private List<Relation> collectRelationsToEntities(List<RelationDTO> relationDTOList, List<ModelEntity> modelEntityList) {
        List<Relation> relationList = new ArrayList<>();
        for (var relationDTO : relationDTOList) {
            ModelEntity fromEntity = null;
            ModelEntity toEntity = null;
            for (var modelEntity : modelEntityList) {
                if (relationDTO.getFromEntity().equals(modelEntity.getTitle())) {
                    fromEntity = modelEntity;
                }
                if (relationDTO.getToEntity().equals(modelEntity.getTitle())) {
                    toEntity = modelEntity;
                }
                if (fromEntity != null && toEntity != null) break;
            }
            if (fromEntity == null || toEntity == null) {
                throw new RelationDoesNotMatchEntityException(
                        "Relation (entity1=%s/entity2=%s) does not match with entities in table!".formatted(relationDTO.getFromEntity(), relationDTO.getToEntity())
                );
            }
            relationList.add(Relation.builder()
                    .modelEntity1(fromEntity)
                    .modelEntity2(toEntity)
                    .power(Power.fromString(relationDTO.getPower()))
                    .build()
            );
        }
        return relationList;
    }
}

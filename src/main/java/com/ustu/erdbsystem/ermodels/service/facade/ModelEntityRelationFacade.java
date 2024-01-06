package com.ustu.erdbsystem.ermodels.service.facade;

import com.ustu.erdbsystem.ermodels.api.dto.ModelDTO;
import com.ustu.erdbsystem.ermodels.api.dto.ModelEntityDTO;
import com.ustu.erdbsystem.ermodels.api.dto.RelationDTO;
import com.ustu.erdbsystem.ermodels.api.dto.response.ModelDetailDTO;
import com.ustu.erdbsystem.ermodels.api.mapper.AttributeDTOMapper;
import com.ustu.erdbsystem.ermodels.api.mapper.ModelDetailDTOMapper;
import com.ustu.erdbsystem.ermodels.api.mapper.ModelEntityDTOMapper;
import com.ustu.erdbsystem.ermodels.api.mapper.RelationDTOMapper;
import com.ustu.erdbsystem.ermodels.service.ModelEntityAttributeService;
import com.ustu.erdbsystem.ermodels.service.ModelService;
import com.ustu.erdbsystem.ermodels.service.RelationService;
import com.ustu.erdbsystem.ermodels.store.models.Model;
import com.ustu.erdbsystem.ermodels.store.models.ModelEntity;
import com.ustu.erdbsystem.persons.api.mapper.PersonDTOMapper;
import com.ustu.erdbsystem.persons.store.models.Person;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Component
public class ModelEntityRelationFacade {
    private final ModelService modelService;
    private final ModelEntityAttributeService modelEntityAttributeService;
    private final RelationService relationService;

    public Model createModelWithRelatedEntities(ModelDTO modelDTO,
                            Person person,
                            List<ModelEntityDTO> modelEntityDTOList,
                            List<RelationDTO> relationDTOList) {
        Model model = modelService.create(modelDTO, person);
        List<ModelEntity> modelEntityList =
                modelEntityAttributeService.createEntitiesWithAttributes(modelEntityDTOList, model);
        relationService.createEntitiesRelations(relationDTOList, modelEntityList);
        return model;
    }

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

    public void deleteModelWithRelations(Model model) {
        relationService.deleteRelationsFromModel(model);
        modelService.deleteModel(model);
    }
}

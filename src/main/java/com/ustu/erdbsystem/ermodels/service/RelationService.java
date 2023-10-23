package com.ustu.erdbsystem.ermodels.service;

import com.ustu.erdbsystem.ermodels.api.dto.RelationDTO;
import com.ustu.erdbsystem.ermodels.store.models.Model;
import com.ustu.erdbsystem.ermodels.store.models.ModelEntity;
import com.ustu.erdbsystem.ermodels.store.models.Relation;

import java.util.List;

public interface RelationService {
    List<Relation> createEntitiesRelations(List<RelationDTO> relationDTOList, List<ModelEntity> modelEntityList);

    void deleteRelationsFromModel(Model model);

    List<Relation> getRelationsByEntityIds(List<Long> modelEntityIdList);
}

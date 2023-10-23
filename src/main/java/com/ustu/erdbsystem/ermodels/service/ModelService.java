package com.ustu.erdbsystem.ermodels.service;

import com.ustu.erdbsystem.ermodels.api.dto.ModelDTO;
import com.ustu.erdbsystem.ermodels.api.dto.ModelEntityDTO;
import com.ustu.erdbsystem.ermodels.api.dto.RelationDTO;
import com.ustu.erdbsystem.ermodels.store.models.Model;
import com.ustu.erdbsystem.ermodels.store.models.ModelEntity;
import com.ustu.erdbsystem.ermodels.store.models.Relation;
import com.ustu.erdbsystem.persons.store.models.Person;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface ModelService {
    List<Model> getAll();

    List<Model> getAll(List<Long> idList);

    Optional<Model> getById(Long id);

    void deleteModel(Model model);

    Model create(Person person,
                 ModelDTO modelDTO,
                 List<ModelEntityDTO> modelEntityDTOList,
                 List<RelationDTO> relationDTOList);
}

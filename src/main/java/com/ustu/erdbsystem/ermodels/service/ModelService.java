package com.ustu.erdbsystem.ermodels.service;

import com.ustu.erdbsystem.ermodels.api.dto.ModelDTO;
import com.ustu.erdbsystem.ermodels.api.dto.ModelEntityDTO;
import com.ustu.erdbsystem.ermodels.api.dto.RelationDTO;
import com.ustu.erdbsystem.ermodels.api.dto.response.ModelDetailDTO;
import com.ustu.erdbsystem.ermodels.store.models.Model;
import com.ustu.erdbsystem.persons.store.models.Person;

import java.util.List;
import java.util.Optional;

public interface ModelService {

    List<Model> getAll(Integer page, Integer size, Boolean includeStudents, Boolean includeTaskResults);

    Optional<Model> getById(Long id);

    ModelDetailDTO getModelDetailDTOByModel(Model model);

    List<Model> getAllByPerson(Person person);

    void deleteModel(Model model);

    Model create(ModelDTO modelDTO,
                 List<ModelEntityDTO> modelEntityDTOList,
                 List<RelationDTO> relationDTOList,
                 Person person);
}

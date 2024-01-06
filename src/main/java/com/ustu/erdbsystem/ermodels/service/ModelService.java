package com.ustu.erdbsystem.ermodels.service;

import com.ustu.erdbsystem.ermodels.api.dto.ModelDTO;
import com.ustu.erdbsystem.ermodels.api.dto.ModelEntityDTO;
import com.ustu.erdbsystem.ermodels.api.dto.RelationDTO;
import com.ustu.erdbsystem.ermodels.api.dto.response.ModelDetailDTO;
import com.ustu.erdbsystem.ermodels.store.models.Model;
import com.ustu.erdbsystem.persons.store.models.Person;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ModelService {

    List<Model> getAll(Pageable pageable, Boolean includeStudents, Boolean includeTaskResults);

    Optional<Model> getById(Long id);

    List<Model> getAllByPerson(Person person);

    void deleteModel(Model model);

    Model create(ModelDTO modelDTO, Person person);
}

package com.ustu.erdbsystem.ermodels.service;

import com.ustu.erdbsystem.ermodels.api.dto.CreateModelDTO;
import com.ustu.erdbsystem.ermodels.store.models.Model;

import java.util.List;
import java.util.Optional;

public interface ModelService {
    List<Model> getAll();
    List<Model> getAll(List<Long> idList);

    Optional<Model> getById(Long id);

    Long create(CreateModelDTO createModelDTO);
}

package com.ustu.erdbsystem.ermodels.service;

import com.ustu.erdbsystem.ermodels.api.dto.ModelEntityDTO;
import com.ustu.erdbsystem.ermodels.store.models.Model;
import com.ustu.erdbsystem.ermodels.store.models.ModelEntity;
import jakarta.transaction.Transactional;

import java.util.List;

public interface ModelEntityAttributeService {
    List<ModelEntity> createEntitiesWithAttributes(List<ModelEntityDTO> modelEntityDTO, Model model);

    List<ModelEntity> getAllByModel(Model model);
}

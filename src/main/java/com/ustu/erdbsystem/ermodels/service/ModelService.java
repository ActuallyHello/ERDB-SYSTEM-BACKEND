package com.ustu.erdbsystem.ermodels.service;

import com.ustu.erdbsystem.ermodels.dto.ModelDetailDTO;
import com.ustu.erdbsystem.ermodels.dto.ModelPreviewDTO;
import com.ustu.erdbsystem.ermodels.store.models.Model;

import java.util.List;
import java.util.Optional;

public interface ModelService {
    List<ModelPreviewDTO> getAll();
    List<ModelPreviewDTO> getAll(List<Long> idList);

    Optional<ModelDetailDTO> getById(Long id);


}

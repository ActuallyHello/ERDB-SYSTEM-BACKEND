package com.ustu.erdbsystem.ermodels.service.impl;

import com.ustu.erdbsystem.ermodels.dto.ModelDetailDTO;
import com.ustu.erdbsystem.ermodels.dto.ModelPreviewDTO;
import com.ustu.erdbsystem.ermodels.factory.impl.ModelDetailDTOFactory;
import com.ustu.erdbsystem.ermodels.factory.impl.ModelPreviewDTOFactory;
import com.ustu.erdbsystem.ermodels.service.ModelService;
import com.ustu.erdbsystem.ermodels.store.repos.ModelRepo;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
public class ModelServiceImpl implements ModelService {

    private ModelRepo modelRepo;
    private ModelPreviewDTOFactory modelPreviewDTOFactory;
    private ModelDetailDTOFactory modelDetailDTOFactory;

    @Override
    @Transactional
    public List<ModelPreviewDTO> getAll() {
        return modelRepo.findAll().stream()
                .map(modelPreviewDTOFactory::makeDTO)
                .toList();
    }

    @Override
    @Transactional
    public List<ModelPreviewDTO> getAll(List<Long> idList) {
        return modelRepo.findByIdIn(idList).stream()
                .map(modelPreviewDTOFactory::makeDTO)
                .toList();
    }

    @Override
    @Transactional
    public Optional<ModelDetailDTO> getById(Long id) {
        return modelRepo.findById(id).map(modelDetailDTOFactory::makeDTO);
    }
}

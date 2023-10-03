package com.ustu.erdbsystem.ermodels.service.impl;

import com.ustu.erdbsystem.ermodels.api.dto.CreateModelDTO;
import com.ustu.erdbsystem.ermodels.api.mapper.impl.ModelDetailDTOMapper;
import com.ustu.erdbsystem.ermodels.api.mapper.impl.ModelPreviewDTOMapper;
import com.ustu.erdbsystem.ermodels.service.ModelService;
import com.ustu.erdbsystem.ermodels.store.models.Attribute;
import com.ustu.erdbsystem.ermodels.store.models.Model;
import com.ustu.erdbsystem.ermodels.store.models.ModelEntity;
import com.ustu.erdbsystem.ermodels.store.repos.AttributeRepo;
import com.ustu.erdbsystem.ermodels.store.repos.ModelEntityRepo;
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
    private ModelEntityRepo modelEntityRepo;
    private AttributeRepo attributeRepo;

    private ModelPreviewDTOMapper modelPreviewDTOFactory;
    private ModelDetailDTOMapper modelDetailDTOFactory;

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

    /**
     * CREATE A MODEL WITH ENTITIES AND THEIR ATTRIBUTES
     * Create model object, then iterate through model entities
     *  and then iterate through entity attributes
     * @param createModelDTO - dto from client
     * @return - model's id
     */
    @Override
    @Transactional
    public Long create(CreateModelDTO createModelDTO) {
        Model model = Model.builder()
                .title(createModelDTO.title())
                .description(createModelDTO.description())
                .topic(createModelDTO.topic())
                .person(createModelDTO.person())
                .isTaskResult(createModelDTO.isTaskResult())
                .build();
        for (var modelEntityDTO : createModelDTO.modelEntityDTOList()) {
            ModelEntity modelEntity = ModelEntity.builder()
                            .title(modelEntityDTO.title())
                            .model(model)
                            .build();
            for (var attributeDTO : modelEntityDTO.attributeDTOList()) {
                Attribute attribute = Attribute.builder()
                        .title(attributeDTO.title())
                        .attributeType(attributeDTO.attributeType())
                        .modelEntity(modelEntity)
                        .build();
                modelEntity.getAttributeList().add(attribute);
            }
            model.getModelEntityList().add(modelEntity);
        }
        return modelRepo.saveAndFlush(model).getId();
    }
}

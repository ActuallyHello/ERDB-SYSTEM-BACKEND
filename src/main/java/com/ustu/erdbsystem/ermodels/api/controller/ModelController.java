package com.ustu.erdbsystem.ermodels.api.controller;

import com.ustu.erdbsystem.ermodels.api.dto.ModelPreviewDTO;
import com.ustu.erdbsystem.ermodels.api.mapper.impl.ModelPreviewDTOMapper;
import com.ustu.erdbsystem.ermodels.service.ModelService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/models")
public class ModelController {

    private ModelService modelService;
    private ModelPreviewDTOMapper modelPreviewDTOMapper;

    @GetMapping
    public ResponseEntity<List<ModelPreviewDTO>> getAllPreviewModels() {
        var result = modelService.getAll().stream()
                .map(modelPreviewDTOMapper::makeDTO)
                .toList();
        return ResponseEntity.ok(result);
    }
}

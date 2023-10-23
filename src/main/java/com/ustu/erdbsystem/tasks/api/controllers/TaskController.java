package com.ustu.erdbsystem.tasks.api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ustu.erdbsystem.ermodels.store.repos.ModelRepo;
import com.ustu.erdbsystem.tasks.store.models.DenormalizeModel;
import com.ustu.erdbsystem.tasks.store.models.Task;
import com.ustu.erdbsystem.tasks.store.repos.DenormalizeModelRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("tasks/")
public class TaskController {

    private DenormalizeModelRepo denormalizeModelRepo;
    private ModelRepo modelRepo;
    @GetMapping
    public String test() throws JsonProcessingException {
        System.out.println(modelRepo.findById(1L).orElseThrow());
//        System.out.println(modelRepo.findById(1L).orElseThrow().getEntityList());
//        System.out.println(modelRepo.findById(1L).orElseThrow().getEntityList().size());
//        System.out.println(modelRepo.findById(1L).orElseThrow().getEntityList());
//        Map<String, Object> data = new HashMap<>();
//        data.put("test", "test-test");
//        data.put("models", Map.of("entity1", "entity2", "relation1", "relation2"));
//        data.put("yes", Map.of());
//        ObjectMapper objectMapper = new ObjectMapper();
//        String jacksonData = objectMapper.writeValueAsString(data);
//        denormalizeModelRepo.save(DenormalizeModel.builder()
//                .view(jacksonData)
//                .build()
//        );
//        Optional<DenormalizeModel> result = denormalizeModelRepo.findById(1L);
//        String json = result.orElseThrow(() -> new RuntimeException("yes!")).getView();
//        System.out.println(json);
//        var a = objectMapper.readValue(json, HashMap.class);
//        System.out.println(a);
        return "123 321 123 321";
    }
}

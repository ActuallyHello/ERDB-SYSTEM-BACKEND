package com.ustu.erdbsystem.tasks.api.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ustu.erdbsystem.ermodels.exception.response.ModelNotFoundException;
import com.ustu.erdbsystem.ermodels.service.ModelEntityAttributeService;
import com.ustu.erdbsystem.ermodels.service.ModelService;
import com.ustu.erdbsystem.ermodels.store.repos.ModelRepo;
import com.ustu.erdbsystem.persons.api.mapper.PersonDTOMapper;
import com.ustu.erdbsystem.persons.api.mapper.PositionDTOMapper;
import com.ustu.erdbsystem.persons.api.mapper.TeacherDTOMapper;
import com.ustu.erdbsystem.persons.exception.response.TeacherNotFoundException;
import com.ustu.erdbsystem.persons.service.TeacherService;
import com.ustu.erdbsystem.tasks.api.dtos.request.CreateTaskRequestDTO;
import com.ustu.erdbsystem.tasks.api.dtos.response.TaskWithDenormalizeModelDTO;
import com.ustu.erdbsystem.tasks.api.dtos.response.TaskWithTeacherDTO;
import com.ustu.erdbsystem.tasks.api.mapper.TaskDTOMapper;
import com.ustu.erdbsystem.tasks.api.mapper.TaskWithTeacherDTOMapper;
import com.ustu.erdbsystem.tasks.exception.response.TaskNotFoundException;
import com.ustu.erdbsystem.tasks.exception.service.TaskDoesNotExistException;
import com.ustu.erdbsystem.tasks.service.DenormalizeModelService;
import com.ustu.erdbsystem.tasks.service.TaskService;
import com.ustu.erdbsystem.tasks.store.models.DenormalizeModel;
import com.ustu.erdbsystem.tasks.store.models.Task;
import com.ustu.erdbsystem.tasks.store.repos.DenormalizeModelRepo;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("tasks/")
public class TaskController {

    private final TaskService taskService;
    private final DenormalizeModelService denormalizeModelService;
    private final TeacherService teacherService;
    private final ModelService modelService;
    private final ModelEntityAttributeService modelEntityAttributeService;

    @GetMapping
    public ResponseEntity<List<TaskWithTeacherDTO>> getAllTasksWithTeachers() {
        var taskWithTeacherDTOList = taskService.getAllWithTeachers().stream()
                .map(task -> {
                    var teacher = task.getTeacher();
                    var person = teacher.getPerson();
                    var position = teacher.getPosition();
                    return TaskWithTeacherDTOMapper.makeDTO(
                            task,
                            TeacherDTOMapper.makeDTO(
                                    teacher,
                                    PersonDTOMapper.makeDTO(person),
                                    PositionDTOMapper.makeDTO(position)));
                })
                .toList();
        return ResponseEntity.ok(taskWithTeacherDTOList);
    }
    @GetMapping("/{id}")
    public ResponseEntity<TaskWithDenormalizeModelDTO> getTaskWithDenormalizeModelById(@RequestParam Long id) {
        var task = taskService.getByIdWithDenormalizeModel(id)
                .orElseThrow(() -> new TaskNotFoundException("Task with id=%d was not found!".formatted(id)));
        System.out.println(task);
        return ResponseEntity.ok(null);
    }

    @PostMapping
    public ResponseEntity<Object> createTask(@RequestBody @Valid CreateTaskRequestDTO createTaskRequestDTO) {
        var taskDTO = TaskDTOMapper.makeDTO(createTaskRequestDTO);
        var teacher = teacherService.getByIdWithTasks(createTaskRequestDTO.getTeacherId())
                .orElseThrow(() -> new TeacherNotFoundException("Teacher with id=%d was not found!".formatted(createTaskRequestDTO.getTeacherId())));
        var modelList = createTaskRequestDTO.getModelIds().stream()
                .map(modelId -> {
                    var model = modelService.getById(modelId)
                            .orElseThrow(() -> new ModelNotFoundException("Model with id=%d was not found!".formatted(modelId)));
                    model.setModelEntityList(modelEntityAttributeService.getAllByModel(model));
                    return model;
                })
                .toList();
        List<DenormalizeModel> denormalizeModelList = new ArrayList<>();
        System.out.println(modelList);
        denormalizeModelList = modelList.stream()
                .map(denormalizeModelService::create)
                .toList();
        //TODO
        // 1. DO I NEED MODEL ATTRIBUTE SERIVCE?
        // 2. RESOLVE PROMBLEM LAZY INIT IN MODEL,GETENTITIES
        // 3. EXCEL
        var taskId = taskService.create(taskDTO, teacher, denormalizeModelList);
        return ResponseEntity.ok(Map.of("taskId", taskId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTaskById(@RequestParam Long id) {
        return ResponseEntity.ok(null);
    }


    private DenormalizeModelRepo denormalizeModelRepo;
    private ModelRepo modelRepo;
//    @GetMapping
//    public String abc123321() throws JsonProcessingException {
//        System.out.println(modelRepo.findById(1L).orElseThrow());
////        System.out.println(modelRepo.findById(1L).orElseThrow().getEntityList());
////        System.out.println(modelRepo.findById(1L).orElseThrow().getEntityList().size());
////        System.out.println(modelRepo.findById(1L).orElseThrow().getEntityList());
////        Map<String, Object> data = new HashMap<>();
////        data.put("test", "test-test");
////        data.put("models", Map.of("entity1", "entity2", "relation1", "relation2"));
////        data.put("yes", Map.of());
////        ObjectMapper objectMapper = new ObjectMapper();
////        String jacksonData = objectMapper.writeValueAsString(data);
////        denormalizeModelRepo.save(DenormalizeModel.builder()
////                .view(jacksonData)
////                .build()
////        );
////        Optional<DenormalizeModel> result = denormalizeModelRepo.findById(1L);
////        String json = result.orElseThrow(() -> new RuntimeException("yes!")).getView();
////        System.out.println(json);
////        var a = objectMapper.readValue(json, HashMap.class);
////        System.out.println(a);
//        return "123 321 123 321";
//    }
}

package com.ustu.erdbsystem.persons.api.controller;

import com.ustu.erdbsystem.persons.api.dto.GroupDTO;
import com.ustu.erdbsystem.persons.api.dto.request.CreateGroupRequestDTO;
import com.ustu.erdbsystem.persons.api.mapper.GroupDTOMapper;
import com.ustu.erdbsystem.persons.exception.response.GroupServerException;
import com.ustu.erdbsystem.persons.exception.response.GroupNotFoundException;
import com.ustu.erdbsystem.persons.exception.service.GroupCreationException;
import com.ustu.erdbsystem.persons.exception.service.GroupDeleteException;
import com.ustu.erdbsystem.persons.service.GroupService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;

    private static final String BY_ID = "/{id}";

    @GetMapping
    public ResponseEntity<List<GroupDTO>> getGroups(@RequestParam(required = false) Boolean isActive) {
        if (isActive == null) isActive = true;
        var groupDTOList = groupService.getAll(isActive).stream()
                .map(GroupDTOMapper::makeDTO)
                .toList();
        return ResponseEntity.ok(groupDTOList);
    }

    @GetMapping(BY_ID)
    public ResponseEntity<GroupDTO> getGroupById(@PathVariable Long id) {
        var groupDTO = groupService.getById(id)
                .map(GroupDTOMapper::makeDTO)
                .orElseThrow(() -> new GroupNotFoundException("Group with id=%d was not found!".formatted(id)));
        return ResponseEntity.ok(groupDTO);
    }

    @PostMapping
    public ResponseEntity<Object> createGroup(@RequestBody @Valid CreateGroupRequestDTO createGroupRequestDTO) {
        var groupDTO = GroupDTOMapper.makeDTO(createGroupRequestDTO);
        try {
            var group = groupService.create(groupDTO);
            return new ResponseEntity<>(Map.of("id", group.getId()), HttpStatus.CREATED);
        } catch (GroupCreationException exception) {
            throw new GroupServerException(exception.getMessage(), exception);
        }
    }

    @DeleteMapping(BY_ID)
    public ResponseEntity<Object> deleteGroupById(@PathVariable Long id) {
        var group = groupService.getById(id)
                .orElseThrow(() -> new GroupNotFoundException("Group with id=%d was not found!".formatted(id)));
        try {
            groupService.delete(group);
            return ResponseEntity.noContent().build();
        } catch (GroupDeleteException exception) {
            throw new GroupServerException(exception.getMessage(), exception);
        }
    }

    @PatchMapping(BY_ID)
    public ResponseEntity<GroupDTO> updateGroupById(@PathVariable Long id,
                                                    @RequestBody @Valid CreateGroupRequestDTO createGroupRequestDTO) {
        var group = groupService.getById(id)
                .orElseThrow(() -> new GroupNotFoundException("Group with id=%d was not found!".formatted(id)));
        group.setTitle(createGroupRequestDTO.getTitle());
        try {
            group = groupService.update(group);
            return ResponseEntity.ok(GroupDTOMapper.makeDTO(group));
        } catch (GroupDeleteException exception) {
            throw new GroupServerException(exception.getMessage(), exception);
        }
    }
}

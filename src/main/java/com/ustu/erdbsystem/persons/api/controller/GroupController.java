package com.ustu.erdbsystem.persons.api.controller;

import com.ustu.erdbsystem.persons.api.dto.GroupDTO;
import com.ustu.erdbsystem.persons.api.dto.request.CreateGroupRequestDTO;
import com.ustu.erdbsystem.persons.api.mapper.GroupDTOMapper;
import com.ustu.erdbsystem.persons.exception.response.GroupDBException;
import com.ustu.erdbsystem.persons.exception.response.GroupNotFoundException;
import com.ustu.erdbsystem.persons.exception.service.GroupCreationException;
import com.ustu.erdbsystem.persons.exception.service.GroupDeleteException;
import com.ustu.erdbsystem.persons.service.GroupService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/groups")
public class GroupController {

    private final GroupService groupService;

    @GetMapping("")
    public ResponseEntity<List<GroupDTO>> getGroups() {
        var groupDTOList = groupService.getAll(true).stream()
                .map(GroupDTOMapper::makeDTO)
                .toList();
        return ResponseEntity.ok(groupDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupDTO> getGroupById(@PathVariable Long id) {
        var groupDTO = groupService.getById(id)
                .map(GroupDTOMapper::makeDTO)
                .orElseThrow(() -> new GroupNotFoundException("Group with id=%d was not found!".formatted(id)));
        return ResponseEntity.ok(groupDTO);
    }

    @PostMapping("")
    public ResponseEntity<Long> createGroup(@RequestBody CreateGroupRequestDTO createGroupRequestDTO) {
        var groupDTO = GroupDTOMapper.makeDTO(createGroupRequestDTO);
        try {
            var group = groupService.create(groupDTO);
            return ResponseEntity.ok(group.getId());
        } catch (GroupCreationException exception) {
            throw new GroupDBException("Error when creating group! " + exception.getMessage(), exception);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteGroupById(@PathVariable Long id) {
        var group = groupService.getById(id)
                .orElseThrow(() -> new GroupNotFoundException("Group with id=%d was not found!".formatted(id)));
        try {
            groupService.delete(group);
            return ResponseEntity.noContent().build();
        } catch (GroupDeleteException exception) {
            throw new GroupDBException("Error when deleting group! " + exception.getMessage(), exception);
        }
    }
}

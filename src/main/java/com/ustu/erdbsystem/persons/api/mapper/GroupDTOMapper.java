package com.ustu.erdbsystem.persons.api.mapper;

import com.ustu.erdbsystem.persons.api.dto.GroupDTO;
import com.ustu.erdbsystem.persons.api.dto.request.CreateGroupRequestDTO;
import com.ustu.erdbsystem.persons.store.models.Group;

public class GroupDTOMapper {
    public static GroupDTO makeDTO(Group group) {
        if (group == null) throw  new IllegalArgumentException("group is null");
        return GroupDTO.builder()
                .id(group.getId())
                .title(group.getTitle())
                .build();
    }

    public static GroupDTO makeDTO(CreateGroupRequestDTO createGroupRequestDTO) {
        return GroupDTO.builder()
                .title(createGroupRequestDTO.getTitle())
                .build();
    }

    public static Group fromDTO(GroupDTO groupDTO) {
        if (groupDTO == null) throw  new IllegalArgumentException("groupDTO is null");
        return Group.builder()
                .id(groupDTO.getId())
                .title(groupDTO.getTitle())
                .isActive(groupDTO.getIsActive())
                .build();
    }

}

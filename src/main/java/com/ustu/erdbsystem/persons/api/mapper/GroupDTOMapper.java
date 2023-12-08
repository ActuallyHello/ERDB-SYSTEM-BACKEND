package com.ustu.erdbsystem.persons.api.mapper;

import com.ustu.erdbsystem.persons.api.dto.GroupDTO;
import com.ustu.erdbsystem.persons.api.dto.request.CreateGroupRequestDTO;
import com.ustu.erdbsystem.persons.store.models.Group;
import lombok.NonNull;

public class GroupDTOMapper {
    public static GroupDTO makeDTO(@NonNull Group group) {
        return GroupDTO.builder()
                .id(group.getId())
                .title(group.getTitle())
                .build();
    }

    public static GroupDTO makeDTO(@NonNull CreateGroupRequestDTO createGroupRequestDTO) {
        return GroupDTO.builder()
                .title(createGroupRequestDTO.getTitle())
                .build();
    }

    public static Group fromDTO(@NonNull GroupDTO groupDTO) {
        return Group.builder()
                .id(groupDTO.getId())
                .title(groupDTO.getTitle())
                .isActive(groupDTO.getIsActive())
                .build();
    }

}

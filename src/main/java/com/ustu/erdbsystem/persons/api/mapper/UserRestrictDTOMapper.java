package com.ustu.erdbsystem.persons.api.mapper;

import com.ustu.erdbsystem.persons.api.dto.response.UserRestrictDTO;
import com.ustu.erdbsystem.persons.store.models.User;
import lombok.NonNull;

public class UserRestrictDTOMapper {
    public static UserRestrictDTO makeDTO(@NonNull User user) {
        return UserRestrictDTO.builder()
                .id(user.getId())
                .login(user.getLogin())
                .email(user.getEmail())
                .isActive(user.getIsActive())
                .build();
    }
}

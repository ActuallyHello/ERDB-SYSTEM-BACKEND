package com.ustu.erdbsystem.persons.api.mapper;

import com.ustu.erdbsystem.persons.api.dto.UserDTO;
import com.ustu.erdbsystem.persons.store.models.User;
import org.springframework.stereotype.Component;

public class UserDTOMapper {

    public static UserDTO makeDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .login(user.getLogin())
                .email(user.getEmail())
                .isActive(user.getIsActive())
                .build();
    }

    public static User fromDTO(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .login(userDTO.getLogin())
                .email(userDTO.getEmail())
                .isActive(userDTO.getIsActive())
                .build();
    }
}

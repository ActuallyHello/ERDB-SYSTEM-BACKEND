package com.ustu.erdbsystem.persons.api.mapper;

import com.ustu.erdbsystem.persons.api.dto.UserDTO;
import com.ustu.erdbsystem.persons.api.dto.request.CreateUserRequestDTO;
import com.ustu.erdbsystem.persons.store.models.User;

public class UserDTOMapper {

    public static UserDTO makeDTO(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .login(user.getLogin())
                .email(user.getEmail())
                .password(user.getPassword())
                .isActive(user.getIsActive())
                .build();
    }

    public static UserDTO makeDTO(CreateUserRequestDTO createUserRequestDTO) {
        return UserDTO.builder()
                .login(createUserRequestDTO.getLogin())
                .email(createUserRequestDTO.getEmail())
                .password(createUserRequestDTO.getPassword())
                .build();
    }

    public static User fromDTO(UserDTO userDTO) {
        return User.builder()
                .id(userDTO.getId())
                .login(userDTO.getLogin())
                .password(userDTO.getPassword())
                .email(userDTO.getEmail())
                .isActive(userDTO.getIsActive())
                .build();
    }
}

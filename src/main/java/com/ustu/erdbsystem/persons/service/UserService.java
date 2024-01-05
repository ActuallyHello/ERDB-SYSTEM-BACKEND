package com.ustu.erdbsystem.persons.service;

import com.ustu.erdbsystem.persons.api.dto.UserDTO;
import com.ustu.erdbsystem.persons.store.models.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAll();

    Optional<User> getById(Long id);

    Optional<User> getByLogin(String login);

    User create(UserDTO userDTO);

    void delete(User user);

    User update(User userNew);
}

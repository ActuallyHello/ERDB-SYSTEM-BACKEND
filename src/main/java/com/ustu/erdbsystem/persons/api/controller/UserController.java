package com.ustu.erdbsystem.persons.api.controller;

import com.ustu.erdbsystem.persons.api.dto.UserDTO;
import com.ustu.erdbsystem.persons.api.dto.UserRestrictDTO;
import com.ustu.erdbsystem.persons.api.dto.request.CreateUserRequestDTO;
import com.ustu.erdbsystem.persons.api.mapper.UserDTOMapper;
import com.ustu.erdbsystem.persons.api.mapper.UserRestrictDTOMapper;
import com.ustu.erdbsystem.persons.exception.response.UserDBException;
import com.ustu.erdbsystem.persons.exception.response.UserNotFoundException;
import com.ustu.erdbsystem.persons.exception.service.UserCreationException;
import com.ustu.erdbsystem.persons.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<List<UserRestrictDTO>> getAllUsers() {
        var userList = userService.getAll().stream()
                .map(UserRestrictDTOMapper::makeDTO)
                .toList();
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRestrictDTO> getUserById(@PathVariable Long id) {
        var user = userService.getById(id)
                .map(UserRestrictDTOMapper::makeDTO)
                .orElseThrow(() -> new UserNotFoundException("User with id=%d was not found!".formatted(id)));
        return ResponseEntity.ok(user);
    }

    @PostMapping("")
    public ResponseEntity<Long> createUser(@RequestBody CreateUserRequestDTO createUserRequestDTO) {
        var userDTO = UserDTOMapper.makeDTO(createUserRequestDTO);
        try {
            var user = userService.create(userDTO);
            return ResponseEntity.ok(user.getId());
        } catch (UserCreationException exception) {
            throw new UserDBException("Error when creating user! " + exception.getMessage(), exception);
        }
    }
}

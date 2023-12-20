package com.ustu.erdbsystem.persons.api.controller;

import com.ustu.erdbsystem.persons.api.dto.response.UserRestrictDTO;
import com.ustu.erdbsystem.persons.api.dto.request.CreateUserRequestDTO;
import com.ustu.erdbsystem.persons.api.mapper.UserDTOMapper;
import com.ustu.erdbsystem.persons.api.mapper.UserRestrictDTOMapper;
import com.ustu.erdbsystem.persons.exception.response.UserServerException;
import com.ustu.erdbsystem.persons.exception.response.UserNotFoundException;
import com.ustu.erdbsystem.persons.exception.service.UserCreationException;
import com.ustu.erdbsystem.persons.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private static final String BY_ID = "/{id}";

    @GetMapping
    public ResponseEntity<List<UserRestrictDTO>> getAllUsers() {
        var userList = userService.getAll().stream()
                .map(UserRestrictDTOMapper::makeDTO)
                .toList();
        return ResponseEntity.ok(userList);
    }

    @GetMapping(BY_ID)
    public ResponseEntity<UserRestrictDTO> getUserById(@PathVariable Long id) {
        var user = userService.getById(id)
                .map(UserRestrictDTOMapper::makeDTO)
                .orElseThrow(() -> new UserNotFoundException("User with id=%d was not found!".formatted(id)));
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody @Valid CreateUserRequestDTO createUserRequestDTO) {
        var userDTO = UserDTOMapper.makeDTO(createUserRequestDTO);
        try {
            var user = userService.create(userDTO);
            return new ResponseEntity<>(Map.of("id", user.getId()), HttpStatus.CREATED);
        } catch (UserCreationException exception) {
            throw new UserServerException(exception.getMessage(), exception);
        }
    }
}

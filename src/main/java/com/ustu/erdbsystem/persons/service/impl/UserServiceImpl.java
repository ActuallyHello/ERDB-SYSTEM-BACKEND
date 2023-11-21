package com.ustu.erdbsystem.persons.service.impl;

import com.ustu.erdbsystem.persons.api.dto.UserDTO;
import com.ustu.erdbsystem.persons.api.mapper.UserDTOMapper;
import com.ustu.erdbsystem.persons.exception.service.UserCreationException;
import com.ustu.erdbsystem.persons.exception.service.UserDeleteException;
import com.ustu.erdbsystem.persons.service.UserService;
import com.ustu.erdbsystem.persons.store.models.User;
import com.ustu.erdbsystem.persons.store.repos.UserRepo;
import jakarta.persistence.PersistenceException;
import org.springframework.transaction.annotation.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    private UserRepo userRepo;

    @Override
    public List<User> getAll() {
        List<User> userList = userRepo.findAll();
        log.info("GET USERS ({})", userList.size());
        return userList;
    }

    @Override
    public Optional<User> getById(Long id) {
        Optional<User> user = userRepo.findById(id);
        log.info("GET USER WITH ID={}", id);
        return user;
    }

    @Override
    @Transactional
    public User create(UserDTO userDTO) {
        var user = UserDTOMapper.fromDTO(userDTO);
        try {
            user = userRepo.saveAndFlush(user);
            log.info("USER WITH ID={} WAS CREATED", user.getId());
            return user;
        } catch (DataIntegrityViolationException | PersistenceException exception) {
            log.error("ERROR WHEN CREATING USER: {}", exception.getMessage());
            throw new UserCreationException("Error when creating user! [DatabaseException]", exception);
        }
    }

    @Override
    @Transactional
    public void delete(User user) {
        try {
            userRepo.delete(user);
            userRepo.flush();
            log.info("USER WITH ID={} WAS DELETED", user.getId());
        } catch (DataIntegrityViolationException exception) {
            log.error("ERROR WHEN DELETING USER: {}", exception.getMessage());
            throw new UserDeleteException("Error when deleting user! [DatabaseException]", exception);
        }
    }

    @Override
    @Transactional
    public User update(User userNew) {
        try {
            User user = userRepo.saveAndFlush(userNew);
            log.info("USER WITH ID={} WAS UPDATED", userNew.getId());
            return user;
        } catch (DataIntegrityViolationException exception) {
            log.error("ERROR WHEN UPDATING USER: {}", exception.getMessage());
            throw new UserCreationException("Error when updating user! [DatabaseException]", exception);
        }
    }
}

package com.ustu.erdbsystem.persons.service.impl;

import com.ustu.erdbsystem.persons.api.dto.GroupDTO;
import com.ustu.erdbsystem.persons.api.mapper.GroupDTOMapper;
import com.ustu.erdbsystem.persons.exception.service.GroupCreationException;
import com.ustu.erdbsystem.persons.exception.service.GroupDeleteException;
import com.ustu.erdbsystem.persons.service.GroupService;
import com.ustu.erdbsystem.persons.store.models.Group;
import com.ustu.erdbsystem.persons.store.repos.GroupRepo;
import jakarta.persistence.PersistenceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
public class GroupServiceImpl implements GroupService {

    private GroupRepo groupRepo;

    @Override
    public List<Group> getAll() {
        var groupList = groupRepo.findAll();
        log.info("GET GROUPS ({})", groupList.size());
        return groupList;
    }

    @Override
    public List<Group> getAll(Boolean isActive) {
        var groupList = groupRepo.findByIsActive(isActive);
        log.info("GET GROUPS BY ACTIVE={} ({})", isActive, groupList.size());
        return groupList;
    }

    @Override
    public Optional<Group> getById(Long id) {
        var group = groupRepo.findById(id);
        log.info("GET GROUP WITH ID={}", id);
        return group;
    }

    @Override
    public Group create(GroupDTO groupDTO) {
        var group = GroupDTOMapper.fromDTO(groupDTO);
        try {
            group = groupRepo.saveAndFlush(group);
            log.info("GROUP WITH ID={} WAS CREATED", group.getId());
            return group;
        } catch (PersistenceException exception) {
            log.error("CANNOT CREATE GROUP: {}", exception.getMessage());
            throw new GroupCreationException(exception.getMessage(), exception);
        }
    }

    @Override
    public void delete(Group group) {
        try {
            groupRepo.delete(group);
            log.info("GROUP WITH ID={} WAS DELETED", group.getId());
        } catch (PersistenceException exception) {
            log.error("CANNOT DELETE GROUP: {}", exception.getMessage());
            throw new GroupDeleteException(exception.getMessage(), exception);
        }
    }

    @Override
    public Group update(Group groupNew) {
        try {
            var group = groupRepo.saveAndFlush(groupNew);
            log.info("GROUP WITH ID={} WAS UPDATED", group.getId());
            return group;
        } catch (PersistenceException exception) {
            log.error("CANNOT UPDATE GROUP: {}", exception.getMessage());
            throw new GroupCreationException(exception.getMessage(), exception);
        }
    }
}

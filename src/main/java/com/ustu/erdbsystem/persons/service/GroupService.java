package com.ustu.erdbsystem.persons.service;

import com.ustu.erdbsystem.persons.api.dto.GroupDTO;
import com.ustu.erdbsystem.persons.store.models.Group;

import java.util.List;
import java.util.Optional;

public interface GroupService {
    List<Group> getAll();

    List<Group> getAll(Boolean isActive);

    Optional<Group> getById(Long id);

    Group create(GroupDTO groupDTO);

    void delete(Group group);

    Group update(Group groupNew);
}

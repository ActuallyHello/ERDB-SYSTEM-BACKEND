package com.ustu.erdbsystem.persons.service;

import com.ustu.erdbsystem.persons.api.dto.PositionDTO;
import com.ustu.erdbsystem.persons.store.models.Position;

import java.util.List;
import java.util.Optional;

public interface PositionService {
    List<Position> getAll();

    Optional<Position> getById(Long id);

    Position create(PositionDTO positionDTO);

    void delete(Position position);

    Position update(Position positionNew);
}

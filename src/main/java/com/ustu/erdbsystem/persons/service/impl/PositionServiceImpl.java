package com.ustu.erdbsystem.persons.service.impl;

import com.ustu.erdbsystem.persons.api.dto.PositionDTO;
import com.ustu.erdbsystem.persons.api.mapper.PositionDTOMapper;
import com.ustu.erdbsystem.persons.exception.service.PositionCreationException;
import com.ustu.erdbsystem.persons.exception.service.PositionDeleteException;
import com.ustu.erdbsystem.persons.service.PositionService;
import com.ustu.erdbsystem.persons.store.models.Position;
import com.ustu.erdbsystem.persons.store.repos.PositionRepo;
import jakarta.persistence.PersistenceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
public class PositionServiceImpl implements PositionService {

    private PositionRepo positionRepo;

    @Override
    public List<Position> getAll() {
        var positionList = positionRepo.findAll();
        log.info("GET POSITIONS ({})", positionList.size());
        return positionList;
    }

    @Override
    public Optional<Position> getById(Long id) {
        var position = positionRepo.findById(id);
        log.info("GET POSITION WITH ID={}", id);
        return position;
    }

    @Override
    public Position create(PositionDTO positionDTO) {
        var position = PositionDTOMapper.fromDTO(positionDTO);
        try {
            position = positionRepo.saveAndFlush(position);
            log.info("POSITION WITH ID={} WAS CREATED", position.getId());
            return position;
        } catch (PersistenceException exception) {
            log.error("CANNOT CREATE POSITION: {}", exception.getMessage());
            throw new PositionCreationException(exception.getMessage(), exception);
        }
    }

    @Override
    public void delete(Position position) {
        try {
            positionRepo.delete(position);
            log.info("POSITION WITH ID={} WAS DELETED", position.getId());
        } catch (PersistenceException exception) {
            log.error("CANNOT DELETE POSITION: {}", exception.getMessage());
            throw new PositionDeleteException(exception.getMessage(), exception);
        }
    }

    @Override
    public Position update(Position positionNew) {
        try {
            var position = positionRepo.saveAndFlush(positionNew);
            log.info("POSITION WITH ID={} WAS CREATED", position.getId());
            return position;
        } catch (PersistenceException exception) {
            log.error("CANNOT UPDATE POSITION: {}", exception.getMessage());
            throw new PositionCreationException(exception.getMessage(), exception);
        }
    }
}

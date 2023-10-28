package com.ustu.erdbsystem.persons.api.controller;

import com.ustu.erdbsystem.persons.api.dto.PositionDTO;
import com.ustu.erdbsystem.persons.api.dto.request.CreatePositionRequestDTO;
import com.ustu.erdbsystem.persons.api.mapper.PositionDTOMapper;
import com.ustu.erdbsystem.persons.exception.response.PositionDBException;
import com.ustu.erdbsystem.persons.exception.response.PositionNotFoundException;
import com.ustu.erdbsystem.persons.exception.service.PositionCreationException;
import com.ustu.erdbsystem.persons.exception.service.PositionDeleteException;
import com.ustu.erdbsystem.persons.service.PositionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/positions")
public class PositionController {

    private final PositionService positionService;

    @GetMapping("")
    public ResponseEntity<List<PositionDTO>> getPositions() {
        var positionDTOList = positionService.getAll().stream()
                .map(PositionDTOMapper::makeDTO)
                .toList();
        return ResponseEntity.ok(positionDTOList);
    }

    @PostMapping("")
    public ResponseEntity<Object> createPosition(@RequestBody CreatePositionRequestDTO createPositionRequestDTO) {
        var positionDTO = PositionDTOMapper.makeDTO(createPositionRequestDTO);
        try {
            var position = positionService.create(positionDTO);
            return ResponseEntity.ok(Map.of("positionId", position.getId()));
        } catch (PositionCreationException exception) {
            throw new PositionDBException("Error when creating position! " + exception.getMessage(), exception);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletePositionById(@PathVariable Long id) {
        var position = positionService.getById(id)
                .orElseThrow(() -> new PositionNotFoundException("Position with id=%d was not found!".formatted(id)));
        try {
            positionService.delete(position);
            return ResponseEntity.noContent().build();
        } catch (PositionDeleteException exception) {
            throw new PositionDBException("Error when deleting position! " + exception.getMessage(), exception);
        }
    }
}

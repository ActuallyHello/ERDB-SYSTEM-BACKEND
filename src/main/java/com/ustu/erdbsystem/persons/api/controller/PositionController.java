package com.ustu.erdbsystem.persons.api.controller;

import com.ustu.erdbsystem.persons.api.dto.PositionDTO;
import com.ustu.erdbsystem.persons.api.dto.request.CreatePositionRequestDTO;
import com.ustu.erdbsystem.persons.api.mapper.PositionDTOMapper;
import com.ustu.erdbsystem.persons.exception.response.PositionServerException;
import com.ustu.erdbsystem.persons.exception.response.PositionNotFoundException;
import com.ustu.erdbsystem.persons.exception.service.PositionCreationException;
import com.ustu.erdbsystem.persons.exception.service.PositionDeleteException;
import com.ustu.erdbsystem.persons.service.PositionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    private static final String BY_ID = "/{id}";

    @GetMapping
    public ResponseEntity<List<PositionDTO>> getPositions() {
        var positionDTOList = positionService.getAll().stream()
                .map(PositionDTOMapper::makeDTO)
                .toList();
        return ResponseEntity.ok(positionDTOList);
    }

    @PostMapping
    public ResponseEntity<Object> createPosition(@RequestBody @Valid CreatePositionRequestDTO createPositionRequestDTO) {
        var positionDTO = PositionDTOMapper.makeDTO(createPositionRequestDTO);
        try {
            var position = positionService.create(positionDTO);
            return new ResponseEntity<>(Map.of("positionId", position.getId()), HttpStatus.CREATED);
        } catch (PositionCreationException exception) {
            throw new PositionServerException(exception.getMessage(), exception);
        }
    }

    @DeleteMapping(BY_ID)
    public ResponseEntity<Object> deletePositionById(@PathVariable Long id) {
        var position = positionService.getById(id)
                .orElseThrow(() -> new PositionNotFoundException("Position with id=%d was not found!".formatted(id)));
        try {
            positionService.delete(position);
            return ResponseEntity.noContent().build();
        } catch (PositionDeleteException exception) {
            throw new PositionServerException(exception.getMessage(), exception);
        }
    }
}

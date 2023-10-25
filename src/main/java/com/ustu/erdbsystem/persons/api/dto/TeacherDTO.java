package com.ustu.erdbsystem.persons.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDTO {
    Long id;
    PersonDTO personDTO;
    PositionDTO positionDTO;
}

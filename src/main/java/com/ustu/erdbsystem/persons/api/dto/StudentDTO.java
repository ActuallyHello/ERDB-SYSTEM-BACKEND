package com.ustu.erdbsystem.persons.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
    Long id;
    PersonDTO personDTO;
    GroupDTO groupDTO;
}

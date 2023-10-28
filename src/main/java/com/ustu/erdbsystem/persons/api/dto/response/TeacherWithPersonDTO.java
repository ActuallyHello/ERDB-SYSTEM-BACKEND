package com.ustu.erdbsystem.persons.api.dto.response;

import com.ustu.erdbsystem.persons.api.dto.PersonDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class TeacherWithPersonDTO {
    private Long id;
    private PersonDTO personDTO;
}

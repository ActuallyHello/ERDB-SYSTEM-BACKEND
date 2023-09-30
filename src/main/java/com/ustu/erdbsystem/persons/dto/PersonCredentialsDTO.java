package com.ustu.erdbsystem.persons.dto;

import com.ustu.erdbsystem.persons.store.models.enums.PersonType;
import lombok.Builder;

@Builder
public record PersonCredentialsDTO(
        Long id,
        String firstName,
        String lastName,
        String middleName,
        PersonType personType
) {
}

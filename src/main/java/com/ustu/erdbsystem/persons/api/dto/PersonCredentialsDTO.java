package com.ustu.erdbsystem.persons.api.dto;

import com.ustu.erdbsystem.persons.store.models.enums.PersonType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonCredentialsDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String personType;
}

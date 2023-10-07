package com.ustu.erdbsystem.persons.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PersonRequestDTO {
    private String firstName;
    private String lastName;
    private String middleName;
    private String personType;

}

package com.ustu.erdbsystem.persons.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequestDTO {
    private String login;
    private String password;
    private String email;
}

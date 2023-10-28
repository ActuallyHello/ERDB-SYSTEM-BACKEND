package com.ustu.erdbsystem.persons.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class UserRestrictDTO {
    private Long id;
    private String login;
    private String email;
    private Boolean isActive;

}

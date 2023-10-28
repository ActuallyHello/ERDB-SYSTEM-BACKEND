package com.ustu.erdbsystem.persons.api.dto.response;

import com.ustu.erdbsystem.persons.api.dto.GroupDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class StudentWithGroupDTO {
    private Long id;
    private GroupDTO groupDTO;
}

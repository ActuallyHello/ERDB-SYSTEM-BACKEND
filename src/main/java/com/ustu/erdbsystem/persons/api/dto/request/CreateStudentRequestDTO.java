package com.ustu.erdbsystem.persons.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateStudentRequestDTO {
    private Long personId;
    private Long groupId;
}

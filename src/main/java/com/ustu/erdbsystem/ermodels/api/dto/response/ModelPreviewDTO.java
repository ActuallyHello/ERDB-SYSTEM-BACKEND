package com.ustu.erdbsystem.ermodels.api.dto.response;

import com.ustu.erdbsystem.ermodels.api.dto.ModelDTO;
import com.ustu.erdbsystem.persons.api.dto.PersonDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModelPreviewDTO {
    private ModelDTO modelDTO;
    private PersonDTO personDTO;
}

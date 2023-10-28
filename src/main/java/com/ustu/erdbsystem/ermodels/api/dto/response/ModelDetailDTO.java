package com.ustu.erdbsystem.ermodels.api.dto.response;

import com.ustu.erdbsystem.ermodels.api.dto.ModelDTO;
import com.ustu.erdbsystem.ermodels.api.dto.ModelEntityDTO;
import com.ustu.erdbsystem.ermodels.api.dto.RelationDTO;
import com.ustu.erdbsystem.persons.api.dto.PersonDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ModelDetailDTO extends ModelDTO {
    private List<ModelEntityDTO> modelEntityDTOList;
    private List<RelationDTO> relationDTOList;
    private PersonDTO personDTO;
}

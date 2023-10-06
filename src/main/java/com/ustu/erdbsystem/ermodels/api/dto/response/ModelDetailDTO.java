package com.ustu.erdbsystem.ermodels.api.dto.response;

import com.ustu.erdbsystem.ermodels.api.dto.ModelDTO;
import com.ustu.erdbsystem.ermodels.api.dto.ModelEntityDTO;
import com.ustu.erdbsystem.ermodels.api.dto.RelationDTO;
import com.ustu.erdbsystem.persons.api.dto.PersonCredentialsDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModelDetailDTO {
    private ModelDTO modelDTO;
    private List<ModelEntityDTO> modelEntityDTOList;
    private List<RelationDTO> relationDTOList;
    private PersonCredentialsDTO personCredentialsDTO;
}

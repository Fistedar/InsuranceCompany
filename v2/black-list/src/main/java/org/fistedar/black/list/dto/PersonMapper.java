package org.fistedar.black.list.dto;

import org.fistedar.black.list.core.api.dto.PersonDTO;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {

    public PersonDTO personDTOMapper(PersonRequestDTO personRequestDTO) {
        return PersonDTO.builder()
                .personFirstName(personRequestDTO.getPersonFirstName())
                .personLastName(personRequestDTO.getPersonLastName())
                .personCode(personRequestDTO.getPersonCode())
                .build();
    }
}

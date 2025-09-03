package org.fistedar.black.list.dto;

import org.fistedar.black.list.core.api.command.BlacklistCoreResult;
import org.fistedar.black.list.core.api.dto.PersonDTO;
import org.springframework.stereotype.Component;

@Component
public class ResponseMapper {

    public BlackListResponse responseMapper(BlacklistCoreResult coreResult) {
        PersonResponseDTO personResponseDTO = personMapper(coreResult);
        return new BlackListResponse(personResponseDTO);
    }

    private PersonResponseDTO personMapper(BlacklistCoreResult coreResult) {
        PersonDTO personDTO = coreResult.getPerson();
        return PersonResponseDTO.builder()
                .personCode(personDTO.getPersonCode())
                .personFirstName(personDTO.getPersonFirstName())
                .personLastName(personDTO.getPersonLastName())
                .blackListed(coreResult.getPerson().getBlackListed())
                .build();
    }
}

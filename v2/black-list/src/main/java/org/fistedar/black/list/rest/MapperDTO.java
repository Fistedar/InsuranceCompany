package org.fistedar.black.list.rest;


import org.fistedar.black.list.core.api.command.BlacklistCoreResult;
import org.fistedar.black.list.core.api.dto.PersonDTO;
import org.fistedar.black.list.core.api.dto.ValidationErrorDTO;
import org.fistedar.black.list.dto.BlackListRequest;
import org.fistedar.black.list.dto.BlackListResponse;
import org.fistedar.black.list.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class MapperDTO {

    public BlackListResponse mapperDTO(BlacklistCoreResult result) {
        return result.hasErrors()
                ? buildErrorResponse(result)
                : buildSuccessResponse(result);
    }

    public PersonDTO mapRequestToPerson(BlackListRequest request) {
        return PersonDTO.builder()
                .personFirstName(request.getPersonFirstName())
                .personLastName(request.getPersonLastName())
                .personCode(request.getPersonCode())
                .build();
    }

    private BlackListResponse buildErrorResponse(BlacklistCoreResult result) {
        List<ValidationError> errors = mapValidationErrors(result.getErrors());
        return new BlackListResponse(errors);
    }

    private BlackListResponse buildSuccessResponse(BlacklistCoreResult result) {
        PersonDTO personDTO = result.getPerson();
        return BlackListResponse.builder()
                .personFirstName(personDTO.getPersonFirstName())
                .personLastName(personDTO.getPersonLastName())
                .personCode(personDTO.getPersonCode())
                .blackListed(personDTO.getBlackListed())
                .build();
    }

    private List<ValidationError> mapValidationErrors(List<ValidationErrorDTO> coreErrors) {
        return coreErrors.stream()
                .map(validationErrorDTO -> new ValidationError(validationErrorDTO.getErrorCode(), validationErrorDTO.getDescription()))
                .toList();
    }

}

package org.javaguru.travel.insurance.core.validation;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class TravelAgreementValidator {

    private final List<TravelAgreementValidationFields> validationFields;

    List<ValidationErrorDTO> validate(AgreementDTO agreementDTO) {
        List<ValidationErrorDTO> errors = getListError(agreementDTO);
        errors.addAll(getSingleError(agreementDTO));
        return errors;
    }

    private List<ValidationErrorDTO> getListError(AgreementDTO agreementDTO) {
        return validationFields.stream()
                .flatMap(validator -> validator.validateList(agreementDTO).stream())
                .collect(Collectors.toList());
    }

    private List<ValidationErrorDTO> getSingleError(AgreementDTO agreementDTO) {
        return validationFields.stream()
                .map(validator -> validator.validate(agreementDTO))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }
}

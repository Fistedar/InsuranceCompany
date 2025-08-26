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
class TravelPersonValidator {

    private final List<TravelPersonValidationFields> travelPersonValidationFields;

    List<ValidationErrorDTO> validate(AgreementDTO agreement) {
        List<ValidationErrorDTO> errors = getSingleError(agreement);
        errors.addAll(getListError(agreement));
        return errors;
    }

    private List<ValidationErrorDTO> getSingleError(AgreementDTO agreement) {
        return agreement.getPersons().stream()
                .flatMap(personDTO -> travelPersonValidationFields.stream()
                        .map(validator -> validator.validate(agreement, personDTO))
                        .filter(Optional::isPresent)
                        .map(Optional::get))
                .collect(Collectors.toList());
    }

    private List<ValidationErrorDTO> getListError(AgreementDTO agreement) {
        return agreement.getPersons().stream()
                .flatMap(personDTO -> travelPersonValidationFields.stream()
                        .flatMap(validator -> validator.validateList(agreement, personDTO).stream()))
                .toList();
    }
}
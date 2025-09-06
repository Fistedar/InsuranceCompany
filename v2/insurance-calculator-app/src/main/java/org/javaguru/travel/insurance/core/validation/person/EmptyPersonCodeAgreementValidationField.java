package org.javaguru.travel.insurance.core.validation.person;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validation.ValidationErrorFactory;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@RequiredArgsConstructor
class EmptyPersonCodeAgreementValidationField extends TravelPersonValidationFieldsImpl {

    private final ValidationErrorFactory errorFactory;
    private final static String ERROR_CODE = "ERROR_CODE_17";

    @Override
    public Optional<ValidationErrorDTO> validate(AgreementDTO agreement, PersonDTO person) {
        return (person.getPersonCode() == null
                || person.getPersonCode().isBlank())
                ? Optional.of(errorFactory.buildError(ERROR_CODE))
                : Optional.empty();
    }
}

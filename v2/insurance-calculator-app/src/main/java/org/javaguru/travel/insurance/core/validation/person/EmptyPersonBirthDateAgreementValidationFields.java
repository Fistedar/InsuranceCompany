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
class EmptyPersonBirthDateAgreementValidationFields extends TravelPersonValidationFieldsImpl {

    private final ValidationErrorFactory errorFactory;

    private static final String ERROR_CODE = "ERROR_CODE_13";

    @Override
    public Optional<ValidationErrorDTO> validate(AgreementDTO agreement, PersonDTO person) {
        return person.getPersonBirthDate() == null
                ? Optional.of(errorFactory.buildError(ERROR_CODE))
                : Optional.empty();
    }
}


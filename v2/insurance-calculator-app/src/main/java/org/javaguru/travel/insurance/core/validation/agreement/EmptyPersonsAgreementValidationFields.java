package org.javaguru.travel.insurance.core.validation.agreement;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validation.ValidationErrorFactory;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EmptyPersonsAgreementValidationFields extends TravelAgreementValidationFieldsImpl {

    private final ValidationErrorFactory errorFactory;
    private static final String ERROR_CODE = "ERROR_CODE_16";


    @Override
    public Optional<ValidationErrorDTO> validate(AgreementDTO data) {
        return (data.getPersons() == null
                || data.getPersons().isEmpty())
                ? Optional.of(errorFactory.buildError(ERROR_CODE))
                : Optional.empty();
    }
}

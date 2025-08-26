package org.javaguru.travel.insurance.core.validation;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
class TravelAgreementValidationImpl implements TravelAgreementValidation {

    private final TravelAgreementValidator agreementValidator;
    private final TravelPersonValidator personValidator;


    @Override
    public List<ValidationErrorDTO> validate(AgreementDTO agreementDTO) {
        List<ValidationErrorDTO> errors = new ArrayList<>();
        errors.addAll(agreementValidator.validate(agreementDTO));
        errors.addAll(personValidator.validate(agreementDTO));
        return errors;
    }

}

package org.javaguru.travel.insurance.core.validation.agreement;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validation.TravelAgreementValidationFields;
import java.util.List;
import java.util.Optional;

abstract class TravelAgreementValidationFieldsImpl implements TravelAgreementValidationFields {

    @Override
    public Optional<ValidationErrorDTO> validate(AgreementDTO agreement) {
        return Optional.empty();
    }

    @Override
    public List<ValidationErrorDTO> validateList(AgreementDTO agreement) {
        return List.of();
    }
}

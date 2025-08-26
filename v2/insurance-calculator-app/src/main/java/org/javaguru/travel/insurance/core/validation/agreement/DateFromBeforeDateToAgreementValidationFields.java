package org.javaguru.travel.insurance.core.validation.agreement;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validation.ValidationErrorFactory;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@RequiredArgsConstructor
class DateFromBeforeDateToAgreementValidationFields extends TravelAgreementValidationFieldsImpl {

    private final ValidationErrorFactory validationErrorFactory;

    @Override
    public Optional<ValidationErrorDTO> validate(AgreementDTO agreementDTO) {
        return (agreementDTO.getAgreementDateTo() != null
                && agreementDTO.getAgreementDateFrom() != null
                && agreementDTO.getAgreementDateTo().isBefore(agreementDTO.getAgreementDateFrom()))
                ? Optional.of(validationErrorFactory.buildError("ERROR_CODE_7"))
                : Optional.empty();
    }
}

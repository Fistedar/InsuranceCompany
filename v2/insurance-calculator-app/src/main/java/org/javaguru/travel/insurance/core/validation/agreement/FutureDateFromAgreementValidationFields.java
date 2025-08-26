package org.javaguru.travel.insurance.core.validation.agreement;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.util.DateTimeUtil;
import org.javaguru.travel.insurance.core.validation.ValidationErrorFactory;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@RequiredArgsConstructor
class FutureDateFromAgreementValidationFields extends TravelAgreementValidationFieldsImpl {

    private final DateTimeUtil dateTimeUtil;
    private final ValidationErrorFactory validationErrorFactory;

    @Override
    public Optional<ValidationErrorDTO> validate(AgreementDTO agreementDTO) {
        return (agreementDTO.getAgreementDateFrom() != null
                && agreementDTO.getAgreementDateFrom().isBefore(dateTimeUtil.getCurrentDate()))
                ? Optional.of(validationErrorFactory.buildError("ERROR_CODE_5"))
                : Optional.empty();
    }
}

package org.javaguru.travel.insurance.core.validation.agreement;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validation.ValidationErrorFactory;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@RequiredArgsConstructor
class EmptyCountryAgreementValidationFields extends TravelAgreementValidationFieldsImpl {

    private static final String ERROR = "ERROR_CODE_10";
    private final ValidationErrorFactory errorFactory;

    @Override
    public Optional<ValidationErrorDTO> validate(AgreementDTO agreementDTO) {
        return (countryIsNullOrBlank(agreementDTO))
                ? Optional.of(errorFactory.buildError(ERROR))
                : Optional.empty();
    }


    private boolean countryIsNullOrBlank(AgreementDTO agreementDTO) {
        return agreementDTO.getCountry() == null || agreementDTO.getCountry().isBlank();
    }
}

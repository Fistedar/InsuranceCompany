package org.javaguru.travel.insurance.core.validation.agreement;


import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.validation.RequestValidator;
import org.javaguru.travel.insurance.core.validation.ValidationErrorFactory;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class EmptyMedicalRiskLimitLevelValidation implements RequestValidator {

    private final ValidationErrorFactory errorFactory;

    private static final String ERROR_CODE = "ERROR_CODE_14";

    @Override
    public Optional<ValidationError> validate(TravelCalculatePremiumRequestV1 request) {
        return (medicalRiskLimitLevelEmpty(request))
                    ? Optional.of(errorFactory.buildError(ERROR_CODE))
                    : Optional.empty();
    }

    private boolean medicalRiskLimitLevelEmpty(TravelCalculatePremiumRequestV1 request) {
        return request.getMedicalRiskLimitLevel() == null || request.getMedicalRiskLimitLevel().isBlank();
    }
}

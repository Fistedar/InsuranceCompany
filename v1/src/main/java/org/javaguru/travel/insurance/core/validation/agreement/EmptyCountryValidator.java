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
class EmptyCountryValidator implements RequestValidator {

    private static final String ERROR = "ERROR_CODE_10";
    private final ValidationErrorFactory errorFactory;

    @Override
    public Optional<ValidationError> validate(TravelCalculatePremiumRequestV1 request) {
        return (countryIsNullOrBlank(request))
                ? Optional.of(errorFactory.buildError(ERROR))
                : Optional.empty();
    }


    private boolean countryIsNullOrBlank(TravelCalculatePremiumRequestV1 request) {
        return request.getCountry() == null || request.getCountry().isBlank();
    }
}

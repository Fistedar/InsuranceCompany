package org.javaguru.travel.insurance.core.validation.agreement;


import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;
import org.javaguru.travel.insurance.core.util.Placeholder;
import org.javaguru.travel.insurance.core.validation.RequestValidator;
import org.javaguru.travel.insurance.core.validation.ValidationErrorFactory;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
class ExistsCountryValidator implements RequestValidator {

    private static final String COUNTRY = "COUNTRY";
    private static final String ERROR_CODE = "ERROR_CODE_11";

    private final ValidationErrorFactory errorFactory;
    private final ClassifierValueRepository classifierValueRepository;

    @Override
    public Optional<ValidationError> validate(TravelCalculatePremiumRequestV1 request) {
        return (countryIsNotEmpty(request)
                && countryNotExistsInDatabase(request))
                ? Optional.of(buildValidationError(request.getCountry()))
                : Optional.empty();
    }

    private boolean countryNotExistsInDatabase(TravelCalculatePremiumRequestV1 request) {
        return classifierValueRepository.findByClassifierTitleAndIc(COUNTRY, request.getCountry()).isEmpty();
    }

    private boolean countryIsNotEmpty(TravelCalculatePremiumRequestV1 request) {
        return request.getCountry() != null && !request.getCountry().isBlank();
    }

    private ValidationError buildValidationError(String riskIc) {
        Placeholder placeholder = new Placeholder("NOT_EXISTING_COUNTRY", riskIc);
        return errorFactory.buildError(ERROR_CODE, placeholder);
    }
}

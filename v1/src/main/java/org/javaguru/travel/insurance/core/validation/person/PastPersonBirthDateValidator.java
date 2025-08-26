package org.javaguru.travel.insurance.core.validation.person;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.validation.RequestValidator;
import org.javaguru.travel.insurance.core.validation.ValidationErrorFactory;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

@Component
@RequiredArgsConstructor
class PastPersonBirthDateValidator implements RequestValidator {

    private final ValidationErrorFactory errorFactory;

    private static final String ERROR_CODE = "ERROR_CODE_12";

    @Override
    public Optional<ValidationError> validate(TravelCalculatePremiumRequestV1 request) {
        return (request.getPersonBirthDate() != null
                && request.getPersonBirthDate().isAfter(LocalDate.now()))
                ? Optional.of(errorFactory.buildError(ERROR_CODE))
                : Optional.empty();
    }
}

package org.javaguru.travel.insurance.core.validation;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class TravelCalculatePremiumRequestValidatorImpl implements TravelCalculatePremiumRequestValidator {

    private final List<RequestValidator> individualValidators;
    private final List<RequestValidationList> bulkValidators;

    @Override
    public List<ValidationError> validate(TravelCalculatePremiumRequestV1 request) {
        List<ValidationError> errors = processBulkValidations(request);
        errors.addAll(processIndividualValidations(request));
        return errors;
    }


    private List<ValidationError> processBulkValidations(TravelCalculatePremiumRequestV1 request) {
        return bulkValidators.stream()
                .flatMap(validator -> validator.validate(request).stream())
                .collect(Collectors.toList());
    }

    private List<ValidationError> processIndividualValidations(TravelCalculatePremiumRequestV1 request) {
        return individualValidators.stream()
                .map(validator -> validator.validate(request))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }
}

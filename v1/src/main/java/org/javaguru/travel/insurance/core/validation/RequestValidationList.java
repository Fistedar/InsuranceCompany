package org.javaguru.travel.insurance.core.validation;

import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.ValidationError;

import java.util.List;

public interface RequestValidationList {
    List<ValidationError> validate(TravelCalculatePremiumRequestV1 request);
}

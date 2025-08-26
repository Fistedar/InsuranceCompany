package org.javaguru.travel.insurance.core.validation.internal;

import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import java.util.List;

public interface TravelAgreementUuidValidation {

    List<ValidationErrorDTO> validate(String uuid);
}

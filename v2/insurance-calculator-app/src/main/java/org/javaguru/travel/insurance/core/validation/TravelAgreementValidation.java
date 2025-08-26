package org.javaguru.travel.insurance.core.validation;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import java.util.List;

public interface TravelAgreementValidation {
    List<ValidationErrorDTO> validate(AgreementDTO agreement);
}

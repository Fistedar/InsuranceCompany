package org.javaguru.travel.insurance.core.validation;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import java.util.List;
import java.util.Optional;

public interface TravelAgreementValidationFields {
    Optional<ValidationErrorDTO> validate(AgreementDTO agreement);

    List<ValidationErrorDTO> validateList(AgreementDTO agreement);


}

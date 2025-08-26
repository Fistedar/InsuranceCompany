package org.javaguru.travel.insurance.core.validation.internal;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.repositories.entity.AgreementRepository;
import org.javaguru.travel.insurance.core.util.Placeholder;
import org.javaguru.travel.insurance.core.validation.ValidationErrorFactory;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
class TravelAgreementUuidValidationImpl implements TravelAgreementUuidValidation {

    private final AgreementRepository agreementRepository;
    private final ValidationErrorFactory validationErrorFactory;

    @Override
    public List<ValidationErrorDTO> validate(String uuid) {
        List<ValidationErrorDTO> errors = new ArrayList<>();
        if (uuid == null || uuid.isBlank()) {
            errors.add(validationErrorFactory.buildError("ERROR_CODE_18"));
        } else if (agreementRepository.findByUuid(uuid).isEmpty()) {
            Placeholder placeholder = new Placeholder("NOT_EXISTING_Uuid", uuid);
            errors.add(validationErrorFactory.buildError("ERROR_CODE_19", placeholder));
        }
        return errors;
    }
}

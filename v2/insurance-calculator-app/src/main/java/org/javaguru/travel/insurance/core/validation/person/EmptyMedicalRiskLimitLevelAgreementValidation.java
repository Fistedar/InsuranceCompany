package org.javaguru.travel.insurance.core.validation.person;


import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validation.ValidationErrorFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@RequiredArgsConstructor
class EmptyMedicalRiskLimitLevelAgreementValidation extends TravelPersonValidationFieldsImpl {

    private final ValidationErrorFactory errorFactory;

    @Value("${medical.risk.limit.level.enabled:false}")
    private Boolean medicalRiskLimitLevelEnabled;

    private static final String ERROR_CODE = "ERROR_CODE_14";

    @Override
    public Optional<ValidationErrorDTO> validate(AgreementDTO agreement, PersonDTO person) {
        return (medicalRiskLimitLevelEmpty(person)
                && medicalRiskLimitLevelEnabled)
                ? Optional.of(errorFactory.buildError(ERROR_CODE))
                : Optional.empty();
    }

    private boolean medicalRiskLimitLevelEmpty(PersonDTO person) {
        return person.getMedicalRiskLimitLevel() == null || person.getMedicalRiskLimitLevel().isBlank();
    }
}

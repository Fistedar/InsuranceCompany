package org.javaguru.travel.insurance.core.validation.person;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.repositories.medicalRisk.MedicalRiskLimitLevelRepository;
import org.javaguru.travel.insurance.core.util.Placeholder;
import org.javaguru.travel.insurance.core.validation.ValidationErrorFactory;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@RequiredArgsConstructor
class MedicalRiskLimitLevelExistsInDatabaseAgreementValidation extends TravelPersonValidationFieldsImpl {

    private final ValidationErrorFactory errorFactory;
    private final MedicalRiskLimitLevelRepository repository;

    private static final String ERROR_CODE = "ERROR_CODE_15";

    @Override
    public Optional<ValidationErrorDTO> validate(AgreementDTO agreement, PersonDTO person) {
        return (medicalRiskLimitLevelNotEmpty(person)
                && medicalRiskLimitLevelNotExistsInDataBase(person))
                ? Optional.of(buildError(person))
                : Optional.empty();
    }

    private boolean medicalRiskLimitLevelNotEmpty(PersonDTO personDTO) {
        return personDTO.getMedicalRiskLimitLevel() != null && !personDTO.getMedicalRiskLimitLevel().isBlank();
    }

    private ValidationErrorDTO buildError(PersonDTO personDTO) {
        Placeholder placeholder = new Placeholder("NOT_EXISTING_MedicalRiskLimitLevel", personDTO.getMedicalRiskLimitLevel());
        return errorFactory.buildError(ERROR_CODE, placeholder);
    }

    private boolean medicalRiskLimitLevelNotExistsInDataBase(PersonDTO personDTO) {
        return repository.findByMedicalRiskLimit(personDTO.getMedicalRiskLimitLevel()).isEmpty();
    }

}

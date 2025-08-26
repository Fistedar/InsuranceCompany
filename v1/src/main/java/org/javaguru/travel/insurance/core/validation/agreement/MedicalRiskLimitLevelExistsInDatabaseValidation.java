package org.javaguru.travel.insurance.core.validation.agreement;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.repositories.MedicalRiskLimitLevelRepository;
import org.javaguru.travel.insurance.core.util.Placeholder;
import org.javaguru.travel.insurance.core.validation.RequestValidator;
import org.javaguru.travel.insurance.core.validation.ValidationErrorFactory;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
class MedicalRiskLimitLevelExistsInDatabaseValidation implements RequestValidator {

    private final ValidationErrorFactory errorFactory;
    private final MedicalRiskLimitLevelRepository repository;

    private static final String ERROR_CODE = "ERROR_CODE_15";

    @Override
    public Optional<ValidationError> validate(TravelCalculatePremiumRequestV1 request) {
        return (medicalRiskLimitLevelNotEmpty(request)
                && medicalRiskLimitLevelNotExistsInDataBase(request))
                ? Optional.of(buildError(request))
                : Optional.empty();
    }

    private boolean medicalRiskLimitLevelNotEmpty(TravelCalculatePremiumRequestV1 request) {
        return request.getMedicalRiskLimitLevel() != null && !request.getMedicalRiskLimitLevel().isBlank();
    }

    private ValidationError buildError(TravelCalculatePremiumRequestV1 request) {
        Placeholder placeholder = new Placeholder("NOT_EXISTING_MedicalRiskLimitLevel",request.getMedicalRiskLimitLevel());
        return errorFactory.buildError(ERROR_CODE,placeholder);
    }

    private boolean medicalRiskLimitLevelNotExistsInDataBase(TravelCalculatePremiumRequestV1 request) {
        return repository.findByMedicalRiskLimit(request.getMedicalRiskLimitLevel()).isEmpty();
    }

}

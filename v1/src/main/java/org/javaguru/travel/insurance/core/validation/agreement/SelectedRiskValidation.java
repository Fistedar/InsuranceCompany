package org.javaguru.travel.insurance.core.validation.agreement;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.repositories.ClassifierValueRepository;
import org.javaguru.travel.insurance.core.util.Placeholder;
import org.javaguru.travel.insurance.core.validation.RequestValidationList;
import org.javaguru.travel.insurance.core.validation.ValidationErrorFactory;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class SelectedRiskValidation implements RequestValidationList {

    private static final String RISK_TYPE = "RISK_TYPE";
    private static final String ERROR_CODE = "ERROR_CODE_9";

    private final ClassifierValueRepository classifierValueRepository;
    private final ValidationErrorFactory validationErrorFactory;

    @Override
    public List<ValidationError> validate(TravelCalculatePremiumRequestV1 request) {
        return request.getSelectedRisks() != null
                ? validateSelectedRisks(request)
                : List.of();
    }

    private List<ValidationError> validateSelectedRisks(TravelCalculatePremiumRequestV1 request) {
        return request.getSelectedRisks().stream()
                .filter(this::riskNotExistsInDatabase)
                .map(this::buildValidationError)
                .collect(Collectors.toList());
    }

    private boolean riskNotExistsInDatabase(String ic) {
        return classifierValueRepository.findByClassifierTitleAndIc(RISK_TYPE, ic).isEmpty();
    }

    private ValidationError buildValidationError(String riskIc) {
        Placeholder placeholder = new Placeholder("NOT_EXISTING_RISK_TYPE", riskIc);
        return validationErrorFactory.buildError(ERROR_CODE, placeholder);
    }
}

package org.javaguru.travel.insurance.core.validation.agreement;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.repositories.medicalRisk.ClassifierValueRepository;
import org.javaguru.travel.insurance.core.util.Placeholder;
import org.javaguru.travel.insurance.core.validation.ValidationErrorFactory;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
class SelectedRiskAgreementValidationFields extends TravelAgreementValidationFieldsImpl {

    private static final String RISK_TYPE = "RISK_TYPE";
    private static final String ERROR_CODE = "ERROR_CODE_9";

    private final ClassifierValueRepository classifierValueRepository;
    private final ValidationErrorFactory validationErrorFactory;

    @Override
    public List<ValidationErrorDTO> validateList(AgreementDTO agreementDTO) {
        return agreementDTO.getSelectedRisks() != null
                ? validateSelectedRisks(agreementDTO)
                : List.of();
    }

    private List<ValidationErrorDTO> validateSelectedRisks(AgreementDTO agreementDTO) {
        return agreementDTO.getSelectedRisks().stream()
                .filter(this::riskNotExistsInDatabase)
                .map(this::buildValidationError)
                .collect(Collectors.toList());
    }

    private boolean riskNotExistsInDatabase(String ic) {
        return classifierValueRepository.findByClassifierTitleAndIc(RISK_TYPE, ic).isEmpty();
    }

    private ValidationErrorDTO buildValidationError(String riskIc) {
        Placeholder placeholder = new Placeholder("NOT_EXISTING_RISK_TYPE", riskIc);
        return validationErrorFactory.buildError(ERROR_CODE, placeholder);
    }
}

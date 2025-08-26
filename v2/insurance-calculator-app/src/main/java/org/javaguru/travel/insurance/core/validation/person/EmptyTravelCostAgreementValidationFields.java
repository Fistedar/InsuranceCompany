package org.javaguru.travel.insurance.core.validation.person;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.validation.ValidationErrorFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.Optional;

@Component
@RequiredArgsConstructor
class EmptyTravelCostAgreementValidationFields extends TravelPersonValidationFieldsImpl {

    private final ValidationErrorFactory errorFactory;
    private static final String ERROR_CODE = "ERROR_CODE_20";

    @Value("${cancellation.risk.enabled:false}")
    private Boolean travelCostEnabled;

    @Override
    public Optional<ValidationErrorDTO> validate(AgreementDTO agreement, PersonDTO person) {
        return (travelCostEnabled
                && (cancellationRiskIsPresent(agreement) && travelCostIsInvalid(person)))
                ? Optional.of(errorFactory.buildError(ERROR_CODE))
                : Optional.empty();
    }

    private boolean cancellationRiskIsPresent(AgreementDTO agreement) {
        if (agreement.getSelectedRisks() != null && !agreement.getSelectedRisks().isEmpty()) {
            return agreement.getSelectedRisks().contains("TRAVEL_CANCELLATION");
        }
        return false;
    }

    private boolean travelCostIsInvalid(PersonDTO person) {
        return person.getTravelCost() == null || person.getTravelCost().compareTo(BigDecimal.ZERO) <= 0;
    }
}

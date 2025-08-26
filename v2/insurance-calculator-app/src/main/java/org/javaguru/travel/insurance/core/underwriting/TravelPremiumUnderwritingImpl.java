package org.javaguru.travel.insurance.core.underwriting;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.RiskDTO;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TravelPremiumUnderwritingImpl implements TravelPremiumUnderwriting {

    private final SelectedRisksPremiumCalculator selectedRisksPremiumCalculator;

    @Override
    public TravelPremiumCalculationResult calculateTotalRiskPremium(AgreementDTO agreementDTO, PersonDTO personDTO) {
        List<RiskDTO> risks = createListRisks(agreementDTO, personDTO);
        BigDecimal totalRiskPremium = calculateTotalPremium(agreementDTO, personDTO);
        return new TravelPremiumCalculationResult(totalRiskPremium, risks);
    }

    private List<RiskDTO> createListRisks(AgreementDTO agreementDTO, PersonDTO personDTO) {
        return selectedRisksPremiumCalculator.createListRisks(agreementDTO, personDTO);
    }

    private BigDecimal calculateTotalPremium(AgreementDTO agreementDTO, PersonDTO personDTO) {
        List<RiskDTO> risks = selectedRisksPremiumCalculator.createListRisks(agreementDTO, personDTO);
        return risks.stream()
                .map(RiskDTO::getPremium)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

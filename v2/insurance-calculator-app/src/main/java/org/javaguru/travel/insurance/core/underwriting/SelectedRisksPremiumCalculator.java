package org.javaguru.travel.insurance.core.underwriting;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.RiskDTO;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
class SelectedRisksPremiumCalculator {

    private final List<TravelRiskPremiumCalculator> travelRiskPremiumCalculators;

    List<RiskDTO> createListRisks(AgreementDTO agreementDTO, PersonDTO personDTO) {
        return agreementDTO.getSelectedRisks().stream()
                .map(riskIc -> RiskDTO.builder()
                        .riskIc(riskIc)
                        .premium(riskCalculate(agreementDTO, personDTO, riskIc))
                        .build())
                .toList();
    }

    private BigDecimal riskCalculate(AgreementDTO agreementDTO, PersonDTO personDTO, String riskIc) {
        return getCalculatorByRiskIc(riskIc).calculateSingleRiskPremium(agreementDTO, personDTO);
    }

    private TravelRiskPremiumCalculator getCalculatorByRiskIc(String riskIc) {
        return travelRiskPremiumCalculators.stream()
                .filter(risk -> risk.getRiskIc().equals(riskIc))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Not found " + riskIc + " calculator"));
    }
}

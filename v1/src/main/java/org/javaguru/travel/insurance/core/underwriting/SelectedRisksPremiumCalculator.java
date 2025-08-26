package org.javaguru.travel.insurance.core.underwriting;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.TravelRisk;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
class SelectedRisksPremiumCalculator {

    private final List<TravelRiskPremiumCalculator> travelRiskPremiumCalculators;

    List<TravelRisk> createListRisks(TravelCalculatePremiumRequestV1 request) {
        return request.getSelectedRisks().stream()
                .map(riskIc -> new TravelRisk(riskIc, riskCalculator(request, riskIc)))
                .toList();
    }

    private BigDecimal riskCalculator(TravelCalculatePremiumRequestV1 request, String riskIc) {
        return hasInRequest(riskIc).calculateSingleRiskPremium(request);
    }

    private TravelRiskPremiumCalculator hasInRequest(String riskIc) {
        return travelRiskPremiumCalculators.stream()
                .filter(risk -> risk.getRiskIc().equals(riskIc))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Not found " + riskIc + " calculator"));
    }
}

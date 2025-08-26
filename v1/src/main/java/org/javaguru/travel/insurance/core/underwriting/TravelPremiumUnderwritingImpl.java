package org.javaguru.travel.insurance.core.underwriting;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.TravelRisk;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class TravelPremiumUnderwritingImpl implements TravelPremiumUnderwriting {

    private final SelectedRisksPremiumCalculator selectedRisksPremiumCalculator;

    @Override
    public RiskPremium calculateTotalRiskPremium(TravelCalculatePremiumRequestV1 request) {
        List<TravelRisk> risks = createListRisks(request);
        BigDecimal totalRiskPremium = calculateTotalPremium(request);
        return new RiskPremium(risks, totalRiskPremium);
    }

    private List<TravelRisk> createListRisks(TravelCalculatePremiumRequestV1 request) {
        return selectedRisksPremiumCalculator.createListRisks(request);
    }

    private BigDecimal calculateTotalPremium(TravelCalculatePremiumRequestV1 request) {
        List<TravelRisk> risks = selectedRisksPremiumCalculator.createListRisks(request);
        return risks.stream()
                .map(TravelRisk::getPremium)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

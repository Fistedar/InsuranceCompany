package org.javaguru.travel.insurance.core.underwriting.calculators;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.underwriting.TravelRiskPremiumCalculator;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
class TravelCancellationRiskCalculator implements TravelRiskPremiumCalculator {

    @Override
    public BigDecimal calculateSingleRiskPremium(TravelCalculatePremiumRequestV1 request) {
        return BigDecimal.ZERO;
    }

    @Override
    public String getRiskIc() {
        return "TRAVEL_CANCELLATION";
    }
}

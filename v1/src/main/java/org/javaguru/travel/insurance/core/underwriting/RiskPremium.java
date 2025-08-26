package org.javaguru.travel.insurance.core.underwriting;

import org.javaguru.travel.insurance.dto.TravelRisk;

import java.math.BigDecimal;
import java.util.List;

public record RiskPremium(
        List<TravelRisk> risksPremium,
        BigDecimal totalPremium
) {
}
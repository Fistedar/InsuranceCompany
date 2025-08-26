package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.underwriting.TravelRiskPremiumCalculator;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@RequiredArgsConstructor
class TravelMedicalRiskCalculator implements TravelRiskPremiumCalculator {

    private final AgeCoefficientCalculator ageCoefficientCalculator;
    private final CountryDefaultDayRateCalculator countryDefaultDayRateCalculator;
    private final DayCountCalculator dayCountCalculator;
    private final MedicalRiskLimitLevelCalculator medicalRiskLimitLevelCalculator;

    @Override
    public BigDecimal calculateSingleRiskPremium(TravelCalculatePremiumRequestV1 request) {
        BigDecimal ageCoefficient = ageCoefficientCalculator.calculateAgeCoefficient(request);
        BigDecimal dailyRate = countryDefaultDayRateCalculator.calculateDailyRate(request);
        BigDecimal daysCount = dayCountCalculator.calculateDaysCount(request);
        BigDecimal medicalRisk = medicalRiskLimitLevelCalculator.calculateMedicalRisk(request);
        return dailyRate
                .multiply(daysCount)
                .multiply(ageCoefficient)
                .multiply(medicalRisk)
                .setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String getRiskIc() {
        return "TRAVEL_MEDICAL";
    }

}

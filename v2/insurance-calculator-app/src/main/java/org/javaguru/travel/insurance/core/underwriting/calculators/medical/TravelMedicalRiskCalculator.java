package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.underwriting.TravelRiskPremiumCalculator;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
@RequiredArgsConstructor
class TravelMedicalRiskCalculator implements TravelRiskPremiumCalculator {

    private final TMAgeCoefficientCalculator TMAgeCoefficientCalculator;
    private final CountryDefaultDayRateCalculator countryDefaultDayRateCalculator;
    private final DayCountCalculator dayCountCalculator;
    private final MedicalRiskLimitLevelCalculator medicalRiskLimitLevelCalculator;

    @Override
    public BigDecimal calculateSingleRiskPremium(AgreementDTO agreementDTO, PersonDTO personDTO) {
        BigDecimal ageCoefficient = TMAgeCoefficientCalculator.calculateAgeCoefficient(personDTO);
        BigDecimal dailyRate = countryDefaultDayRateCalculator.calculateDailyRate(agreementDTO);
        BigDecimal daysCount = dayCountCalculator.calculateDaysCount(agreementDTO);
        BigDecimal medicalRisk = medicalRiskLimitLevelCalculator.calculateMedicalRisk(personDTO);
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

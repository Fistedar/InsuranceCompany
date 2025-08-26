package org.javaguru.travel.insurance.core.underwriting.calculators.cancellation;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.underwriting.TravelRiskPremiumCalculator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Component
@RequiredArgsConstructor
class TravelCancellationRiskCalculator implements TravelRiskPremiumCalculator {

    private final CountrySafetyRatingCoefficientCalculator countrySafetyRatingCoefficientCalculator;
    private final TCAgeCoefficientCalculator tcAgeCoefficientCalculator;
    private final TravelCostCoefficientCalculator travelCostCoefficientCalculator;

    @Value("${travel.cost.enabled:false}")
    private boolean cancellationRiskEnabled;

    @Override
    public BigDecimal calculateSingleRiskPremium(AgreementDTO agreementDTO, PersonDTO personDTO) {
        if (cancellationRiskEnabled) {
            return getSumAllCoefficients(agreementDTO, personDTO);
        } else {
            return BigDecimal.ZERO;
        }

    }

    private BigDecimal getSumAllCoefficients(AgreementDTO agreementDTO, PersonDTO personDTO) {
        BigDecimal countrySafetyRatingCoefficient =
                countrySafetyRatingCoefficientCalculator.getCountrySafetyRatingCoefficient(agreementDTO.getCountry(), personDTO);
        BigDecimal tcAgeCoefficient =
                tcAgeCoefficientCalculator.getAgeCoefficient(calculateAge(personDTO.getPersonBirthDate()));
        BigDecimal travelCostCoefficient =
                travelCostCoefficientCalculator.getTravelCostCoefficient(personDTO);
        return countrySafetyRatingCoefficient.add(tcAgeCoefficient).add(travelCostCoefficient);
    }

    private int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    @Override
    public String getRiskIc() {
        return "TRAVEL_CANCELLATION";
    }
}

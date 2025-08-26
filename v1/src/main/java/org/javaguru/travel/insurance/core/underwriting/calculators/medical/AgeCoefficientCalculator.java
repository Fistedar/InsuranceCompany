package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.domain.AgeCoefficient;
import org.javaguru.travel.insurance.core.repositories.AgeCoefficientRepository;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Component
@RequiredArgsConstructor
public class AgeCoefficientCalculator {

    private final AgeCoefficientRepository ageCoefficientRepository;

    @Value("${age.coefficient.enabled:false}")
    private Boolean ageCoefficientEnabled;

    BigDecimal calculateAgeCoefficient(TravelCalculatePremiumRequestV1 request) {
        return ageCoefficientEnabled
                ? getAgeCoefficient(request)
                : getDefaultAgeCoefficient();
    }

    private int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    private BigDecimal getAgeCoefficient(TravelCalculatePremiumRequestV1 request) {
        return ageCoefficientRepository.findByAgeFrom(calculateAge(request.getPersonBirthDate()))
                .map(AgeCoefficient::getCoefficient)
                .orElseThrow(() -> new RuntimeException("Age coefficient not found by birthDate = " + request.getPersonBirthDate()));
    }

    private BigDecimal getDefaultAgeCoefficient() {
        return BigDecimal.ONE;
    }
}

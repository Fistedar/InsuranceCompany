package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.domain.medicalRisk.TMAgeCoefficient;
import org.javaguru.travel.insurance.core.repositories.medicalRisk.TMAgeCoefficientRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

@Component
@RequiredArgsConstructor
public class TMAgeCoefficientCalculator {

    private final TMAgeCoefficientRepository TMAgeCoefficientRepository;

    @Value("${age.coefficient.enabled:false}")
    private Boolean ageCoefficientEnabled;

    BigDecimal calculateAgeCoefficient(PersonDTO personDTO) {
        return ageCoefficientEnabled
                ? getAgeCoefficient(personDTO)
                : getDefaultAgeCoefficient();
    }

    private int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    private BigDecimal getAgeCoefficient(PersonDTO personDTO) {
        return TMAgeCoefficientRepository.findByAgeFrom(calculateAge(personDTO.getPersonBirthDate()))
                .map(TMAgeCoefficient::getCoefficient)
                .orElseThrow(() -> new RuntimeException("Age coefficient not found by birthDate = " + personDTO.getPersonBirthDate()));
    }

    private BigDecimal getDefaultAgeCoefficient() {
        return BigDecimal.ONE;
    }
}

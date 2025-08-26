package org.javaguru.travel.insurance.core.underwriting.calculators.cancellation;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.domain.cancellationRisk.CountrySafetyRatingCoefficientEntity;
import org.javaguru.travel.insurance.core.repositories.cancellationRisk.CountrySafetyRatingCoefficientRepository;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class CountrySafetyRatingCoefficientCalculator {

    private final CountrySafetyRatingCoefficientRepository repository;

    BigDecimal getCountrySafetyRatingCoefficient(String country, PersonDTO person) {
        return calculateCoefficient(country, person);
    }

    private BigDecimal calculateCoefficient(String country, PersonDTO person) {
        CountrySafetyRatingCoefficientEntity entity = repository.findByCountry(country)
                .orElseThrow(() -> new RuntimeException("Country " + country + " not found in DB"));
        BigDecimal travelCost = person.getTravelCost();
        BigDecimal coefficient = entity.getCoefficient();
        if (coefficient.compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException("Division by zero - coefficient cannot be zero");
        }
        return travelCost.multiply(coefficient);
    }
}

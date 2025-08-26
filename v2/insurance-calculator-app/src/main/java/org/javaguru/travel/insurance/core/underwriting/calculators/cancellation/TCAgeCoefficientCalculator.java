package org.javaguru.travel.insurance.core.underwriting.calculators.cancellation;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.domain.cancellationRisk.TCAgeCoefficientEntity;
import org.javaguru.travel.insurance.core.repositories.cancellationRisk.TCAgeCoefficientRepository;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class TCAgeCoefficientCalculator {

    private final TCAgeCoefficientRepository repository;

    BigDecimal getAgeCoefficient(int age) {
        TCAgeCoefficientEntity entity = repository.findByAge(age)
                .orElseThrow(() -> new RuntimeException("Age " + age + " is out of supported range"));
        return entity.getCoefficient();
    }
}

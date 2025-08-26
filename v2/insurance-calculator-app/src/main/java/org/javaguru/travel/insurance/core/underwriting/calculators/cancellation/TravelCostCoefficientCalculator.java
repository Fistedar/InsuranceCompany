package org.javaguru.travel.insurance.core.underwriting.calculators.cancellation;

import lombok.RequiredArgsConstructor;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.domain.cancellationRisk.TravelCostCoefficientEntity;
import org.javaguru.travel.insurance.core.repositories.cancellationRisk.TravelCostCoefficientRepository;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class TravelCostCoefficientCalculator {

    private final TravelCostCoefficientRepository travelCostCoefficientRepository;

    BigDecimal getTravelCostCoefficient(PersonDTO personDTO) {
        BigDecimal travelCost = personDTO.getTravelCost();
        TravelCostCoefficientEntity entity = travelCostCoefficientRepository.findTravelCostCoefficientByCost(travelCost)
                .orElseThrow(() -> new RuntimeException("Travel cost coefficient not found. " + travelCost + " isn't supported"));
        return entity.getCoefficient();
    }

}

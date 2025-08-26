package org.javaguru.travel.insurance.core.underwriting.calculators.cancellation;

import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.domain.cancellationRisk.TravelCostCoefficientEntity;
import org.javaguru.travel.insurance.core.repositories.cancellationRisk.TravelCostCoefficientRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TravelCostCoefficientCalculatorTest {

    @Mock
    private PersonDTO personDTO;
    @Mock
    private TravelCostCoefficientEntity entity;
    @Mock
    private TravelCostCoefficientRepository repository;
    @InjectMocks
    private TravelCostCoefficientCalculator calculator;

    @Test
    @DisplayName("Test: Can get travelCostCoefficient")
    void getTravelCostCoefficient() {
        when(repository.findTravelCostCoefficientByCost(personDTO.getTravelCost()))
                .thenReturn(Optional.of(entity));
        when(entity.getCoefficient()).thenReturn(new BigDecimal("1.11"));

        assertEquals(new BigDecimal("1.11"), calculator.getTravelCostCoefficient(personDTO));
    }

    @Test
    @DisplayName("Test: Throw error")
    void throwError_whenTravelCostNotSupported() {
        when(repository.findTravelCostCoefficientByCost(personDTO.getTravelCost()))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> calculator.getTravelCostCoefficient(personDTO));
    }
}
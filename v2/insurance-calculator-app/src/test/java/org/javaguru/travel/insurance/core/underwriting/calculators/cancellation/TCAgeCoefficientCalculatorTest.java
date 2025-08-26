package org.javaguru.travel.insurance.core.underwriting.calculators.cancellation;

import org.javaguru.travel.insurance.core.domain.cancellationRisk.TCAgeCoefficientEntity;
import org.javaguru.travel.insurance.core.repositories.cancellationRisk.TCAgeCoefficientRepository;
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
class TCAgeCoefficientCalculatorTest {

    @Mock
    private TCAgeCoefficientRepository repository;
    @Mock
    private TCAgeCoefficientEntity entity;
    @InjectMocks
    private TCAgeCoefficientCalculator calculator;

    @Test
    @DisplayName("Test: Can get age coefficient")
    void getAgeCoefficient() {
        when(repository.findByAge(20)).thenReturn(Optional.of(entity));
        when(entity.getCoefficient()).thenReturn(new BigDecimal("5.00"));

        assertEquals(new BigDecimal("5.00"), calculator.getAgeCoefficient(20));
    }

    @Test
    @DisplayName("Test: Throw error")
    void shouldThrowError_whenAgeIsOutOfRange() {
        when(repository.findByAge(20)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> calculator.getAgeCoefficient(20));
    }
}
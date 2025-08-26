package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import org.javaguru.travel.insurance.core.domain.AgeCoefficient;
import org.javaguru.travel.insurance.core.repositories.AgeCoefficientRepository;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgeCoefficientCalculatorTest {

    @Mock
    private TravelCalculatePremiumRequestV1 request;

    @Mock
    private AgeCoefficientRepository repository;

    @InjectMocks
    private AgeCoefficientCalculator calculator;

    @Test
    void calculateAgeCoefficient() {
        ReflectionTestUtils.setField(calculator, "ageCoefficientEnabled", Boolean.TRUE);
        AgeCoefficient ageCoefficient = mock(AgeCoefficient.class);
        when(request.getPersonBirthDate()).thenReturn(LocalDate.of(2005,4,4));
        when(repository.findByAgeFrom(20)).thenReturn(Optional.of(ageCoefficient));
        when(ageCoefficient.getCoefficient()).thenReturn(new BigDecimal("1.10"));
        assertEquals(new BigDecimal("1.10"), calculator.calculateAgeCoefficient(request));
    }

    @Test
    void shouldThrowException_whenAgeCoefficientNotFound() {
        ReflectionTestUtils.setField(calculator, "ageCoefficientEnabled", Boolean.TRUE);
        when(request.getPersonBirthDate()).thenReturn(LocalDate.of(2005,4,4));
        when(repository.findByAgeFrom(20)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,() -> calculator.calculateAgeCoefficient(request));
    }

    @Test
    void shouldReturnDefaultAgeCoefficient_whenSwitchOff() {
        ReflectionTestUtils.setField(calculator, "ageCoefficientEnabled", Boolean.FALSE);

        assertEquals(BigDecimal.ONE,calculator.calculateAgeCoefficient(request));
    }
}
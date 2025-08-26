package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.domain.medicalRisk.TMAgeCoefficient;
import org.javaguru.travel.insurance.core.repositories.medicalRisk.TMAgeCoefficientRepository;
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
class TMTMAgeCoefficientCalculatorTest {

    @Mock
    private PersonDTO personDTO;
    @Mock
    private TMAgeCoefficientRepository repository;
    @InjectMocks
    private TMAgeCoefficientCalculator calculator;

    @Test
    void calculateAgeCoefficient() {
        ReflectionTestUtils.setField(calculator, "ageCoefficientEnabled", Boolean.TRUE);
        TMAgeCoefficient ageCoefficient = mock(TMAgeCoefficient.class);
        when(personDTO.getPersonBirthDate()).thenReturn(LocalDate.of(2005, 4, 4));
        when(repository.findByAgeFrom(20)).thenReturn(Optional.of(ageCoefficient));
        when(ageCoefficient.getCoefficient()).thenReturn(new BigDecimal("1.10"));
        assertEquals(new BigDecimal("1.10"), calculator.calculateAgeCoefficient(personDTO));
    }

    @Test
    void shouldThrowException_whenAgeCoefficientNotFound() {
        ReflectionTestUtils.setField(calculator, "ageCoefficientEnabled", Boolean.TRUE);
        when(personDTO.getPersonBirthDate()).thenReturn(LocalDate.of(2005, 4, 4));
        when(repository.findByAgeFrom(20)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> calculator.calculateAgeCoefficient(personDTO));
    }

    @Test
    void shouldReturnDefaultAgeCoefficient_whenSwitchOff() {
        ReflectionTestUtils.setField(calculator, "ageCoefficientEnabled", Boolean.FALSE);

        assertEquals(BigDecimal.ONE, calculator.calculateAgeCoefficient(personDTO));
    }
}
package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.domain.medicalRisk.MedicalRiskLimitLevel;
import org.javaguru.travel.insurance.core.repositories.medicalRisk.MedicalRiskLimitLevelRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MedicalRiskLimitLevelCalculatorTest {

    @Mock
    private PersonDTO personDTO;
    @Mock
    private MedicalRiskLimitLevelRepository repository;
    @InjectMocks
    private MedicalRiskLimitLevelCalculator calculator;

    @Test
    void shouldReturnCoefficient_whenMedicalRiskLimitLevelHasInDatabase() {
        ReflectionTestUtils.setField(calculator, "medicalRiskLimitLevelEnabled", Boolean.TRUE);
        MedicalRiskLimitLevel entity = mock(MedicalRiskLimitLevel.class);
        when(personDTO.getMedicalRiskLimitLevel()).thenReturn("LEVEL_10000");
        when(repository.findByMedicalRiskLimit("LEVEL_10000")).thenReturn(Optional.of(entity));
        when(entity.getCoefficient()).thenReturn(new BigDecimal("1.00"));

        assertEquals(new BigDecimal("1.00"), calculator.calculateMedicalRisk(personDTO));
    }

    @Test
    void shouldThrowException_whenMedicalRiskLimitLevelHasNotInDatabase() {
        ReflectionTestUtils.setField(calculator, "medicalRiskLimitLevelEnabled", Boolean.TRUE);
        when(personDTO.getMedicalRiskLimitLevel()).thenReturn("LEVEL_10000");
        when(repository.findByMedicalRiskLimit("LEVEL_10000")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> calculator.calculateMedicalRisk(personDTO));
    }

    @Test
    void shouldReturnDefaultCoefficient_whenSwitchOff() {
        ReflectionTestUtils.setField(calculator, "medicalRiskLimitLevelEnabled", Boolean.FALSE);

        assertEquals(BigDecimal.ONE, calculator.calculateMedicalRisk(personDTO));
    }
}
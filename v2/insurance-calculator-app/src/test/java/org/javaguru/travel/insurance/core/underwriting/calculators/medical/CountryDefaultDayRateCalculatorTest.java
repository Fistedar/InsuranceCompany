package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.domain.medicalRisk.CountryDefaultDayRate;
import org.javaguru.travel.insurance.core.repositories.medicalRisk.CountryDefaultDayRateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountryDefaultDayRateCalculatorTest {

    @Mock
    private AgreementDTO agreementDTO;
    @Mock
    private CountryDefaultDayRateRepository repository;
    @InjectMocks
    private CountryDefaultDayRateCalculator calculator;

    @Test
    void calculateDailyRate() {
        CountryDefaultDayRate entity = mock(CountryDefaultDayRate.class);
        when(agreementDTO.getCountry()).thenReturn("LATVIA");
        when(repository.findByCountryIc("LATVIA")).thenReturn(Optional.of(entity));
        when(entity.getDefaultDayRate()).thenReturn(new BigDecimal("1.50"));

        assertEquals(new BigDecimal("1.50"), calculator.calculateDailyRate(agreementDTO));
    }

    @Test
    void shouldThrowException_whenCountryDoesNotExist() {
        when(agreementDTO.getCountry()).thenReturn("RUSSIA");
        when(repository.findByCountryIc("RUSSIA")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> calculator.calculateDailyRate(agreementDTO));
    }
}
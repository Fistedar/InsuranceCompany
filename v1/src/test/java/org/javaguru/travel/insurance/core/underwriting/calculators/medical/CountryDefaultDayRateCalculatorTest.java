package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import org.javaguru.travel.insurance.core.domain.CountryDefaultDayRate;
import org.javaguru.travel.insurance.core.repositories.CountryDefaultDayRateRepository;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
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
    private TravelCalculatePremiumRequestV1 request;

    @Mock
    private CountryDefaultDayRateRepository repository;

    @InjectMocks
    private CountryDefaultDayRateCalculator calculator;

    @Test
    void calculateDailyRate() {
        CountryDefaultDayRate entity = mock(CountryDefaultDayRate.class);
        when(request.getCountry()).thenReturn("LATVIA");
        when(repository.findByCountryIc("LATVIA")).thenReturn(Optional.of(entity));
        when(entity.getDefaultDayRate()).thenReturn(new BigDecimal("1.50"));

        assertEquals(new BigDecimal("1.50"), calculator.calculateDailyRate(request));
    }

    @Test
    void shouldThrowException_whenCountryDoesNotExist() {
        when(request.getCountry()).thenReturn("RUSSIA");
        when(repository.findByCountryIc("RUSSIA")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> calculator.calculateDailyRate(request));
    }
}
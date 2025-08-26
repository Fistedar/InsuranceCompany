package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class TravelMedicalRiskCalculatorTest {

    @Mock
    private TravelCalculatePremiumRequestV1 request;

    @Mock
    private AgeCoefficientCalculator ageCoefficientCalculator;

    @Mock
    private DayCountCalculator dayCountCalculator;

    @Mock
    private MedicalRiskLimitLevelCalculator medicalRiskLimitLevelCalculator;

    @Mock
    private CountryDefaultDayRateCalculator countryDefaultDayRateCalculator;

    @InjectMocks
    private TravelMedicalRiskCalculator travelMedicalRiskCalculator;


    @Test
    void calculateSingleRiskPremium() {
        when(dayCountCalculator.calculateDaysCount(request)).thenReturn(new BigDecimal("2"));
        when(ageCoefficientCalculator.calculateAgeCoefficient(request)).thenReturn(new BigDecimal("1"));
        when(countryDefaultDayRateCalculator.calculateDailyRate(request)).thenReturn(new BigDecimal("1.5"));
        when(medicalRiskLimitLevelCalculator.calculateMedicalRisk(request)).thenReturn((new BigDecimal("1.5")));

        assertEquals(new BigDecimal("4.50"), travelMedicalRiskCalculator.calculateSingleRiskPremium(request));
    }
}
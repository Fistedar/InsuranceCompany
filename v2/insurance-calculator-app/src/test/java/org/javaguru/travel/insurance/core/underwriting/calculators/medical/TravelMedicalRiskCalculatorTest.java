package org.javaguru.travel.insurance.core.underwriting.calculators.medical;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
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
    private AgreementDTO agreementDTO;
    @Mock
    private PersonDTO personDTO;
    @Mock
    private TMAgeCoefficientCalculator TMAgeCoefficientCalculator;
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
        when(dayCountCalculator.calculateDaysCount(agreementDTO)).thenReturn(new BigDecimal("2"));
        when(TMAgeCoefficientCalculator.calculateAgeCoefficient(personDTO)).thenReturn(new BigDecimal("1"));
        when(countryDefaultDayRateCalculator.calculateDailyRate(agreementDTO)).thenReturn(new BigDecimal("1.5"));
        when(medicalRiskLimitLevelCalculator.calculateMedicalRisk(personDTO)).thenReturn((new BigDecimal("1.5")));

        assertEquals(new BigDecimal("4.50"), travelMedicalRiskCalculator.calculateSingleRiskPremium(agreementDTO, personDTO));
    }
}
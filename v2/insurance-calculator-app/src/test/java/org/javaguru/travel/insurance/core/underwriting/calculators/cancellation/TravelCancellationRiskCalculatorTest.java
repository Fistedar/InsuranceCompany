package org.javaguru.travel.insurance.core.underwriting.calculators.cancellation;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import java.math.BigDecimal;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TravelCancellationRiskCalculatorTest {

    @Mock
    private PersonDTO personDTO;
    @Mock
    private AgreementDTO agreementDTO;
    @Mock
    private CountrySafetyRatingCoefficientCalculator countrySafetyRatingCoefficientCalculator;
    @Mock
    private TCAgeCoefficientCalculator tcAgeCoefficientCalculator;
    @Mock
    private TravelCostCoefficientCalculator travelCostCoefficientCalculator;
    @InjectMocks
    private TravelCancellationRiskCalculator travelCancellationRiskCalculator;

    @Test
    @DisplayName("Test: Get personal risk premium, when travelCostEnabled ")
    void calculateSingleRiskPremium() {
        ReflectionTestUtils.setField(travelCancellationRiskCalculator, "cancellationRiskEnabled", Boolean.TRUE);
        when(personDTO.getPersonBirthDate()).thenReturn(LocalDate.of(2000, 5, 5));
        when(countrySafetyRatingCoefficientCalculator.getCountrySafetyRatingCoefficient(agreementDTO.getCountry(), personDTO))
                .thenReturn(new BigDecimal("10.15"));
        int ageFromAgeCoefficient = LocalDate.now().getYear() - personDTO.getPersonBirthDate().getYear();
        when(tcAgeCoefficientCalculator.getAgeCoefficient(ageFromAgeCoefficient)).thenReturn(new BigDecimal("5.05"));
        when(travelCostCoefficientCalculator.getTravelCostCoefficient(personDTO)).thenReturn(new BigDecimal("1.20"));

        assertEquals(travelCancellationRiskCalculator.calculateSingleRiskPremium(agreementDTO, personDTO), new BigDecimal("16.40"));
    }

    @Test
    @DisplayName("Test: Get ZERO, when travelCostDisabled")
    void shouldGetZero() {
        ReflectionTestUtils.setField(travelCancellationRiskCalculator, "cancellationRiskEnabled", Boolean.FALSE);

        assertEquals(travelCancellationRiskCalculator.calculateSingleRiskPremium(agreementDTO, personDTO), BigDecimal.ZERO);
    }
}
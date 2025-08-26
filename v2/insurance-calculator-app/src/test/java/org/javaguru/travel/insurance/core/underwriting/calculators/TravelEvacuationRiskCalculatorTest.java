package org.javaguru.travel.insurance.core.underwriting.calculators;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class TravelEvacuationRiskCalculatorTest {

    @Mock
    private AgreementDTO agreementDTO;
    @Mock
    private PersonDTO personDTO;
    @InjectMocks
    private TravelEvacuationRiskCalculator calculator;

    @Test
    @DisplayName("Test: Can get evacuation singleRiskPremium")
    void calculateSingleRiskPremium() {
        assertEquals(calculator.calculateSingleRiskPremium(agreementDTO,personDTO),
                BigDecimal.ZERO);
    }
}
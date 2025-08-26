package org.javaguru.travel.insurance.core.service;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.RiskDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgreementTotalPremiumCalculatorTest {

    @Mock
    private AgreementDTO agreementDTO;
    @Mock
    private PersonDTO personDTO;
    @InjectMocks
    private AgreementTotalPremiumCalculator agreementTotalPremiumCalculator;

    @Test
    void calculateTotalPremium() {
        when(agreementDTO.getPersons()).thenReturn(List.of(personDTO));
        when(personDTO.getRisks()).thenReturn(List.of(new RiskDTO("Risk", new BigDecimal("100.00"))));

        assertEquals(new BigDecimal("100.00"), agreementTotalPremiumCalculator.calculate(agreementDTO));
    }
}
package org.javaguru.travel.insurance.core.underwriting;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.RiskDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SelectedRisksPremiumCalculatorTest {

    private static final String BAGGAGE_RISK_IC = "TRAVEL_LOSS_BAGGAGE";
    private static final String MEDICAL_RISK_IC = "TRAVEL_MEDICAL";
    private static final BigDecimal BAGGAGE_PREMIUM = new BigDecimal("2");
    private static final BigDecimal MEDICAL_PREMIUM = new BigDecimal("5");
    @Mock
    private AgreementDTO agreementDTO;

    @Mock
    private PersonDTO personDTO;

    @Mock
    private TravelRiskPremiumCalculator riskCalculator1;

    @Mock
    private TravelRiskPremiumCalculator riskCalculator2;

    private SelectedRisksPremiumCalculator selectedRisksPremiumCalculator;

    @BeforeEach
    void setUp() {
        List<TravelRiskPremiumCalculator> riskCalculators = List.of(riskCalculator1, riskCalculator2);
        selectedRisksPremiumCalculator = new SelectedRisksPremiumCalculator(riskCalculators);
    }

    @Test
    void couldCreateListRisks_whenGetValidData() {
        when(agreementDTO.getSelectedRisks()).thenReturn(List.of(BAGGAGE_RISK_IC, MEDICAL_RISK_IC));

        when(riskCalculator1.getRiskIc()).thenReturn(BAGGAGE_RISK_IC);
        when(riskCalculator1.calculateSingleRiskPremium(agreementDTO, personDTO)).thenReturn(BAGGAGE_PREMIUM);

        when(riskCalculator2.getRiskIc()).thenReturn(MEDICAL_RISK_IC);
        when(riskCalculator2.calculateSingleRiskPremium(agreementDTO, personDTO)).thenReturn(MEDICAL_PREMIUM);

        List<RiskDTO> expectedRisks = selectedRisksPremiumCalculator.createListRisks(agreementDTO, personDTO);

        assertEquals(expectedRisks.size(), 2);
        assertEquals(expectedRisks.get(0).getRiskIc(), BAGGAGE_RISK_IC);
        assertEquals(expectedRisks.get(1).getRiskIc(), MEDICAL_RISK_IC);
        assertEquals(expectedRisks.get(0).getPremium(), BAGGAGE_PREMIUM);
        assertEquals(expectedRisks.get(1).getPremium(), MEDICAL_PREMIUM);
    }

    @Test
    void shouldThrowException_whenGetInvalidData() {
        when(agreementDTO.getSelectedRisks()).thenReturn(List.of("Abracadabra"));

        assertThrows(RuntimeException.class,
                () -> selectedRisksPremiumCalculator.createListRisks(agreementDTO, personDTO));
    }

}
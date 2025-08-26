package org.javaguru.travel.insurance.core.underwriting;

import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.TravelRisk;
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
    private TravelCalculatePremiumRequestV1 request;

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
        when(request.getSelectedRisks()).thenReturn(List.of(BAGGAGE_RISK_IC, MEDICAL_RISK_IC));

        when(riskCalculator1.getRiskIc()).thenReturn(BAGGAGE_RISK_IC);
        when(riskCalculator1.calculateSingleRiskPremium(request)).thenReturn(BAGGAGE_PREMIUM);

        when(riskCalculator2.getRiskIc()).thenReturn(MEDICAL_RISK_IC);
        when(riskCalculator2.calculateSingleRiskPremium(request)).thenReturn(MEDICAL_PREMIUM);

        List<TravelRisk> expectedRisks = selectedRisksPremiumCalculator.createListRisks(request);

        assertEquals(expectedRisks.size(), 2);
        assertEquals(expectedRisks.get(0).getRiskIc(), BAGGAGE_RISK_IC);
        assertEquals(expectedRisks.get(1).getRiskIc(), MEDICAL_RISK_IC);
        assertEquals(expectedRisks.get(0).getPremium(), BAGGAGE_PREMIUM);
        assertEquals(expectedRisks.get(1).getPremium(), MEDICAL_PREMIUM);
    }

    @Test
    void shouldThrowException_whenGetInvalidData() {
        when(request.getSelectedRisks()).thenReturn(List.of("Abracadabra"));

        assertThrows(RuntimeException.class,
                () -> selectedRisksPremiumCalculator.createListRisks(request));
    }

}
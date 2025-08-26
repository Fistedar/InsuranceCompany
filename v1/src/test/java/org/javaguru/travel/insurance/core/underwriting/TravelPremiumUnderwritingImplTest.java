package org.javaguru.travel.insurance.core.underwriting;

import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.TravelRisk;
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
class TravelPremiumUnderwritingImplTest {
    @Mock
    private TravelCalculatePremiumRequestV1 request;

    @Mock
    private SelectedRisksPremiumCalculator selectedRisksPremiumCalculator;

    @InjectMocks
    private TravelPremiumUnderwritingImpl travelPremiumUnderwritingImpl;

    @Test
    void shouldCalculateTotalPremium_whenGetRisksPremium() {
        when(selectedRisksPremiumCalculator.createListRisks(request))
                .thenReturn(List.of(new TravelRisk("TRAVEL_MEDICAL", BigDecimal.ONE), new TravelRisk("TRAVEL_EVACUATION", BigDecimal.ONE)));

        RiskPremium riskPremium = travelPremiumUnderwritingImpl.calculateTotalRiskPremium(request);
        assertEquals(riskPremium.totalPremium(), BigDecimal.valueOf(2));
    }
}
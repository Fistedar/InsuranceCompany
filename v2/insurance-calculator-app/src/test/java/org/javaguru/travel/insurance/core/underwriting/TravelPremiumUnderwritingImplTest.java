package org.javaguru.travel.insurance.core.underwriting;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.RiskDTO;
import org.junit.jupiter.api.DisplayName;
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
    private AgreementDTO agreementDTO;
    @Mock
    private PersonDTO personDTO;
    @Mock
    private RiskDTO riskDTO;
    @Mock
    private SelectedRisksPremiumCalculator selectedRisksPremiumCalculator;
    @InjectMocks
    private TravelPremiumUnderwritingImpl travelPremiumUnderwritingImpl;

    @Test
    @DisplayName("Test: Calculate total risk premium")
    void shouldCalculateTotalRiskPremium() {
        when(selectedRisksPremiumCalculator.createListRisks(agreementDTO, personDTO))
                .thenReturn(List.of(riskDTO));
        when(riskDTO.getPremium()).thenReturn(new BigDecimal("10"));

        assertEquals(travelPremiumUnderwritingImpl.calculateTotalRiskPremium(agreementDTO, personDTO).totalPremium(),
                new BigDecimal("10"));
    }
}
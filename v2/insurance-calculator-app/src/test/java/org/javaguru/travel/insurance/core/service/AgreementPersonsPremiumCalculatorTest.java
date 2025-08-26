package org.javaguru.travel.insurance.core.service;

import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.RiskDTO;
import org.javaguru.travel.insurance.core.underwriting.TravelPremiumCalculationResult;
import org.javaguru.travel.insurance.core.underwriting.TravelPremiumUnderwriting;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgreementPersonsPremiumCalculatorTest {

    @Mock
    private TravelPremiumUnderwriting travelPremiumUnderwriting;

    @InjectMocks
    private AgreementPersonsPremiumCalculator calculator;

    @Test
    void shouldCalculatePersonRisks() {
        AgreementDTO agreement = new AgreementDTO();
        PersonDTO person = new PersonDTO();
        RiskDTO risk = new RiskDTO();
        agreement.setPersons(List.of(person));

        when(travelPremiumUnderwriting.calculateTotalRiskPremium(agreement, person))
                .thenReturn(new TravelPremiumCalculationResult(BigDecimal.TEN, List.of(risk)));

        calculator.calculateRiskPremiums(agreement);

        assertEquals(List.of(risk), person.getRisks()); // Проверяем что риски установились
        verify(travelPremiumUnderwriting).calculateTotalRiskPremium(agreement, person);
    }
}
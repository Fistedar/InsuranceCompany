package org.javaguru.travel.insurance.core.service;

import org.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreCommand;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.core.messagebroker.MessageSender;
import org.javaguru.travel.insurance.core.service.database.savers.AgreementFactory;
import org.javaguru.travel.insurance.core.validation.TravelAgreementValidation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TravelCalculatePremiumServiceImpTest {

    @Mock
    private AgreementDTO agreementDTO;
    @Mock
    private TravelCalculatePremiumCoreCommand command;
    @Mock
    private TravelAgreementValidation travelAgreementValidation;
    @Mock
    private AgreementPersonsPremiumCalculator agreementPersonsPremiumCalculator;
    @Mock
    private AgreementTotalPremiumCalculator agreementTotalPremiumCalculator;
    @Mock
    private MessageSender messageSender;
    @Mock
    private AgreementFactory agreementFactory;
    @InjectMocks
    private TravelCalculatePremiumServiceImpl premiumService;

    @Test
    void shouldResponseAgreement_whenErrorsIsEmpty() {
        when(command.getAgreement()).thenReturn(agreementDTO);
        when(travelAgreementValidation.validate(agreementDTO)).thenReturn(List.of());
        agreementPersonsPremiumCalculator.calculateRiskPremiums(agreementDTO);
        when(agreementTotalPremiumCalculator.calculate(agreementDTO)).thenReturn(BigDecimal.TEN);
        when(agreementDTO.getAgreementPremium()).thenReturn(BigDecimal.TEN);

        assertEquals(BigDecimal.TEN, premiumService.calculatePremium(command).getAgreement().getAgreementPremium());

        verify(agreementFactory).saveAgreementInDatabase(agreementDTO);
        verify(messageSender).send(agreementDTO);
    }

    @Test
    void shouldErrorResponse_whenHasErrors() {
        when(command.getAgreement()).thenReturn(agreementDTO);
        when(travelAgreementValidation.validate(agreementDTO)).thenReturn(List.of(new ValidationErrorDTO("Error", "Bad Error")));

        assertEquals("Error", premiumService.calculatePremium(command).getErrors().getFirst().getErrorCode());
        assertEquals("Bad Error", premiumService.calculatePremium(command).getErrors().getFirst().getDescription());

        verify(agreementFactory, never()).saveAgreementInDatabase(any());
        verify(messageSender, never()).send(any());
    }

}
package org.javaguru.travel.insurance.core.services;

import org.javaguru.travel.insurance.core.underwriting.RiskPremium;
import org.javaguru.travel.insurance.core.underwriting.TravelPremiumUnderwriting;
import org.javaguru.travel.insurance.core.validation.TravelCalculatePremiumRequestValidator;
import org.javaguru.travel.insurance.dto.*;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumResponseV1;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TravelCalculatePremiumServiceImplTest {

    private TravelCalculatePremiumRequestV1 request;

    @Mock
    private RiskPremium riskPremium;

    @Mock
    private TravelPremiumUnderwriting travelUnderwriting;

    @Mock
    private TravelCalculatePremiumRequestValidator validator;

    @InjectMocks
    private TravelCalculatePremiumServiceImpl service;

    @BeforeEach
    void setUp() {
        request = new TravelCalculatePremiumRequestV1();
        request.setPersonFirstName("Sasha");
        request.setPersonLastName("Ivanov");
        request.setAgreementDateFrom(LocalDate.of(2025, 10, 25));
        request.setAgreementDateTo(LocalDate.of(2025, 10, 27));
    }

    @Test
    void createSuccessResponse_TestFirstName_whenAllFieldFull() {
        when(travelUnderwriting.calculateTotalRiskPremium(request)).thenReturn(riskPremium);
        TravelCalculatePremiumResponseV1 response = service.calculatePremium(request);
        assertEquals(request.getPersonFirstName(), response.getPersonFirstName());
    }

    @Test
    void createSuccessResponse_TestLastName_whenAllFieldFull() {
        when(travelUnderwriting.calculateTotalRiskPremium(request)).thenReturn(riskPremium);
        TravelCalculatePremiumResponseV1 response = service.calculatePremium(request);
        assertEquals(request.getPersonLastName(), response.getPersonLastName());
    }

    @Test
    void createSuccessResponse_TestDateFrom_whenAllFieldFull() {
        when(travelUnderwriting.calculateTotalRiskPremium(request)).thenReturn(riskPremium);
        TravelCalculatePremiumResponseV1 response = service.calculatePremium(request);
        assertEquals(request.getAgreementDateFrom(), response.getAgreementDateFrom());
    }

    @Test
    void createSuccessResponse_TestDateTo_whenAllFieldFull() {
        when(travelUnderwriting.calculateTotalRiskPremium(request)).thenReturn(riskPremium);
        TravelCalculatePremiumResponseV1 response = service.calculatePremium(request);
        assertEquals(request.getAgreementDateTo(), response.getAgreementDateTo());
    }

    @Test
    void createSuccessResponse_TestAgreementPremium_whenAllFieldFull() {
        when(travelUnderwriting.calculateTotalRiskPremium(request)).thenReturn(riskPremium);
        when(riskPremium.totalPremium()).thenReturn(BigDecimal.ONE);
        TravelCalculatePremiumResponseV1 response = service.calculatePremium(request);
        assertEquals(BigDecimal.ONE, response.getAgreementPremium());
    }

    @Test
    void createSuccessResponse_TestListRisks_whenAllFieldFull() {
        List<TravelRisk> travelRisks = List.of(new TravelRisk("TRAVEL_CANCELLATION", BigDecimal.TWO),
                new TravelRisk("TRAVEL_EVACUATION", BigDecimal.TWO));
        when(travelUnderwriting.calculateTotalRiskPremium(request)).thenReturn(riskPremium);
        when(riskPremium.risksPremium()).thenReturn(travelRisks);
        TravelCalculatePremiumResponseV1 response = service.calculatePremium(request);
        assertEquals(travelRisks, response.getRisks());
    }

    @Test
    void createErrorResponse_whenHasErrors() {
        List<ValidationError> errors = List.of(new ValidationError("ERROR_CODE_4", "Field agreementDateTo is empty!"),
                new ValidationError("ERROR_CODE_5", "DateFrom from the past!"));
        when(validator.validate(request)).thenReturn(errors);
        TravelCalculatePremiumResponseV1 response = service.calculatePremium(request);
        assertEquals(errors, response.getErrors());
    }
}
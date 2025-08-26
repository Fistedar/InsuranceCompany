package org.javaguru.travel.insurance.core.services;

import lombok.RequiredArgsConstructor;

import org.javaguru.travel.insurance.core.underwriting.TravelPremiumUnderwriting;
import org.javaguru.travel.insurance.core.validation.TravelCalculatePremiumRequestValidator;
import org.javaguru.travel.insurance.core.underwriting.RiskPremium;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumRequestV1;
import org.javaguru.travel.insurance.dto.v1.TravelCalculatePremiumResponseV1;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class TravelCalculatePremiumServiceImpl implements TravelCalculatePremiumService {

    private final TravelCalculatePremiumRequestValidator validator;
    private final TravelPremiumUnderwriting travelUnderwriting;


    @Override
    public TravelCalculatePremiumResponseV1 calculatePremium(TravelCalculatePremiumRequestV1 request) {
        List<ValidationError> errors = validator.validate(request);
        return errors.isEmpty()
                ? createSuccessResponse(request, travelUnderwriting.calculateTotalRiskPremium(request))
                : createErrorResponse(errors);
    }

    private TravelCalculatePremiumResponseV1 createErrorResponse(List<ValidationError> errors) {
        return new TravelCalculatePremiumResponseV1(errors);
    }

    private TravelCalculatePremiumResponseV1 createSuccessResponse(TravelCalculatePremiumRequestV1 request, RiskPremium premium) {
        return TravelCalculatePremiumResponseV1.builder()
                .personFirstName(request.getPersonFirstName())
                .personLastName(request.getPersonLastName())
                .agreementDateFrom(request.getAgreementDateFrom())
                .agreementDateTo(request.getAgreementDateTo())
                .agreementPremium(premium.totalPremium())
                .personBirthDate(request.getPersonBirthDate())
                .risks(premium.risksPremium())
                .country(request.getCountry())
                .medicalRiskLimitLevel(request.getMedicalRiskLimitLevel())
                .build();
    }


}

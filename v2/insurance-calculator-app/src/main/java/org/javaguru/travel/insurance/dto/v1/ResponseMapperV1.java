package org.javaguru.travel.insurance.dto.v1;

import org.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreResult;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.dto.TravelRisk;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class ResponseMapperV1 {

    public TravelCalculatePremiumResponseV1 buildResponse(TravelCalculatePremiumCoreResult result) {
        return result.hasErrors()
                ? buildErrorResponse(result.getErrors())
                : buildSuccessResponse(result.getAgreement());
    }

    private TravelCalculatePremiumResponseV1 buildSuccessResponse(AgreementDTO agreementDTO) {
        List<TravelRisk> risks = agreementDTO.getPersons().getFirst().getRisks().stream()
                .map(riskDTO -> new TravelRisk(riskDTO.getRiskIc(), riskDTO.getPremium()))
                .toList();

        return TravelCalculatePremiumResponseV1.builder()
                .uuid(agreementDTO.getUuid())
                .personFirstName(agreementDTO.getPersons().getFirst().getPersonFirstName())
                .travelCost(agreementDTO.getPersons().getFirst().getTravelCost())
                .personCode(agreementDTO.getPersons().getFirst().getPersonCode())
                .personLastName(agreementDTO.getPersons().getFirst().getPersonLastName())
                .personBirthDate(agreementDTO.getPersons().getFirst().getPersonBirthDate())
                .medicalRiskLimitLevel(agreementDTO.getPersons().getFirst().getMedicalRiskLimitLevel())
                .agreementPremium(agreementDTO.getAgreementPremium())
                .agreementDateFrom(agreementDTO.getAgreementDateFrom())
                .agreementDateTo(agreementDTO.getAgreementDateTo())
                .risks(risks)
                .country(agreementDTO.getCountry())
                .build();
    }

    private TravelCalculatePremiumResponseV1 buildErrorResponse(List<ValidationErrorDTO> errors) {
        List<ValidationError> errorList = errors.stream()
                .map(validationErrorDTO -> new ValidationError(validationErrorDTO.getErrorCode(), validationErrorDTO.getDescription()))
                .toList();
        return new TravelCalculatePremiumResponseV1(errorList);
    }

}

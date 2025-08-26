package org.javaguru.travel.insurance.dto.v2;

import org.javaguru.travel.insurance.core.api.command.TravelCalculatePremiumCoreResult;
import org.javaguru.travel.insurance.core.api.dto.AgreementDTO;
import org.javaguru.travel.insurance.core.api.dto.PersonDTO;
import org.javaguru.travel.insurance.core.api.dto.RiskDTO;
import org.javaguru.travel.insurance.core.api.dto.ValidationErrorDTO;
import org.javaguru.travel.insurance.dto.TravelRisk;
import org.javaguru.travel.insurance.dto.ValidationError;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.List;

@Component
public class ResponseMapperV2 {

    public TravelCalculatePremiumResponseV2 buildResponse(TravelCalculatePremiumCoreResult result) {
        return result.hasErrors()
                ? buildErrorResponse(result.getErrors())
                : buildSuccessResponse(result.getAgreement());
    }

    private TravelCalculatePremiumResponseV2 buildSuccessResponse(AgreementDTO agreementDTO) {
        return TravelCalculatePremiumResponseV2.builder()
                .uuid(agreementDTO.getUuid())
                .agreementPremium(agreementDTO.getAgreementPremium())
                .agreementDateFrom(agreementDTO.getAgreementDateFrom())
                .persons(getPersons(agreementDTO))
                .agreementDateTo(agreementDTO.getAgreementDateTo())
                .country(agreementDTO.getCountry())
                .build();
    }

    private TravelCalculatePremiumResponseV2 buildErrorResponse(List<ValidationErrorDTO> errors) {
        List<ValidationError> errorList = errors.stream()
                .map(validationErrorDTO -> new ValidationError(validationErrorDTO.getErrorCode(), validationErrorDTO.getDescription()))
                .toList();
        return new TravelCalculatePremiumResponseV2(errorList);
    }

    private List<PersonResponseDTO> getPersons(AgreementDTO agreementDTO) {
        return agreementDTO.getPersons().stream()
                .map(personDTO -> PersonResponseDTO.builder()
                        .personFirstName(personDTO.getPersonFirstName())
                        .personLastName(personDTO.getPersonLastName())
                        .medicalRiskLimitLevel(personDTO.getMedicalRiskLimitLevel())
                        .risks(getRisk(personDTO))
                        .personBirthDate(personDTO.getPersonBirthDate())
                        .personCode(personDTO.getPersonCode())
                        .personPremium(getPersonPremium(personDTO))
                        .travelCost(personDTO.getTravelCost())
                        .build())
                .toList();
    }

    private List<TravelRisk> getRisk(PersonDTO personDTO) {
        return personDTO.getRisks().stream()
                .map(riskDTO -> new TravelRisk(riskDTO.getRiskIc(), riskDTO.getPremium()))
                .toList();
    }

    private BigDecimal getPersonPremium(PersonDTO personDTO) {
        return personDTO.getRisks().stream()
                .map(RiskDTO::getPremium)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

}
